package com.fintern.ourbudgeting.ui.user

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fintern.ourbudgeting.data.repository.HouseholdRepository
import com.fintern.ourbudgeting.data.repository.UserPreferences
import com.fintern.ourbudgeting.data.repository.UserPreferencesRepository
import com.fintern.ourbudgeting.ui.common.model.Household
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(
    private val userPreferencesRepository: UserPreferencesRepository,
    private val householdRepository: HouseholdRepository
) : ViewModel() {

    private val _user: StateFlow<UserPreferences> =
        userPreferencesRepository.readUser()
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000),
                initialValue = UserPreferences()
            )
    val user: StateFlow<UserPreferences> = _user

    val uid: StateFlow<String> = _user.map { it.uid }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), "")
    val nickname: StateFlow<String> = _user.map { it.nickname }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), "")
    val email: StateFlow<String> = _user.map { it.email }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), "")

    fun updateUser(uid: String, nickname: String, email: String) {
        viewModelScope.launch {
            userPreferencesRepository.updateUser(uid, nickname, email)
        }
    }

    private val _household = MutableStateFlow<Household?>(null)
    val household: StateFlow<Household?> = _household.asStateFlow()

    private val _isHouseholdLoading = MutableStateFlow(false)
    val isHouseholdLoading: StateFlow<Boolean> = _isHouseholdLoading.asStateFlow()

    fun initializeUserHousehold() {
        viewModelScope.launch {
            val currentUid = uid.value
            if (currentUid.isNotEmpty()) {
                _isHouseholdLoading.value = true
                val existingHousehold = householdRepository.getUserHousehold(currentUid)
                existingHousehold.fold(
                    onSuccess = { household ->
                        if (household != null) {
                            _household.value = household
                        } else {
                            createNewHousehold(currentUid)
                        }
                    },
                    onFailure = {
                        createNewHousehold(currentUid)
                    }
                )
                _isHouseholdLoading.value = false
            }
        }
    }

    private suspend fun createNewHousehold(userId: String) {
        householdRepository.createInitialHousehold(userId).fold(
            onSuccess = { householdId ->
                householdRepository.getUserHousehold(userId).fold(
                    onSuccess = { household ->
                        _household.value = household
                    },
                    onFailure = {
                        _household.value = null
                    }
                )
            },
            onFailure = {
                _household.value = null
            }
        )
    }
}