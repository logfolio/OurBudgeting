package com.fintern.ourbudgeting.ui.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fintern.ourbudgeting.data.auth.GoogleSignInClient
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
    private val signInClient: GoogleSignInClient,
    private val userViewModel: UserViewModel,
) : ViewModel() {

    private val currentUser = authRepository.getCurrentUser()

    private val _uiState = MutableStateFlow(
        LoginUiState(currentUser = currentUser)
    )
    val uiState = _uiState.asStateFlow()

    fun signInWithGoogle() {
        viewModelScope.launch {
            try {
                val googleIdToken = signInClient.getGoogleIdToken()
                val user = authRepository.signInWithGoogle(googleIdToken)
                _uiState.value = _uiState.value.copy(currentUser = user)
                user?.uid?.let { userViewModel.updateUser(it, user.displayName.toString()) }
            } catch (e: Exception) {
                // TODO: 에러 처리
            }
        }
    }

    fun signOutWithGoogle() {
        viewModelScope.launch {
            authRepository.signOutWithGoogle()
            signInClient.clearCredentialState()
            _uiState.value = _uiState.value.copy(currentUser = null)
        }
    }
}
