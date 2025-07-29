package com.fintern.ourbudgeting.ui.user

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fintern.ourbudgeting.data.repository.UserPreferencesRepository
import com.fintern.ourbudgeting.data.repository.UserPreferences
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(
    private val userPreferencesRepository: UserPreferencesRepository
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

    fun updateUser(uid: String, nickname: String) {
        viewModelScope.launch {
            userPreferencesRepository.updateUser(uid, nickname)
        }
    }
}