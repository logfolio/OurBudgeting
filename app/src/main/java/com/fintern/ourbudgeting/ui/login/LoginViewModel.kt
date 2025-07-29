package com.fintern.ourbudgeting.ui.login

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fintern.ourbudgeting.data.repository.GoogleAuthRepository
import com.fintern.ourbudgeting.ui.user.UserViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val authRepository: GoogleAuthRepository,
    private val userViewModel: UserViewModel,
) : ViewModel() {

    private val currentUser = authRepository.getCurrentUser()

    private val _uiState = MutableStateFlow(
        LoginUiState(currentUser = currentUser)
    )
    val uiState = _uiState.asStateFlow()

    fun signInWithGoogle(context: Context) {
        viewModelScope.launch {
            try {
                val user = authRepository.signInWithGoogle(context)
                _uiState.value = _uiState.value.copy(currentUser = user)
                userViewModel.updateUser(user?.uid ?: "", "닉네임")
            } catch (e: Exception) {
                // TODO: 에러 처리
            }
        }
    }

    fun signOutWithGoogle() {
        viewModelScope.launch {
            authRepository.signOutWithGoogle()
            _uiState.value = _uiState.value.copy(currentUser = null)
        }
    }
}
