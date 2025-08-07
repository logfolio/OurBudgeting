package com.fintern.ourbudgeting.data.repository

import androidx.credentials.ClearCredentialStateRequest
import androidx.credentials.CredentialManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class GoogleAuthRepository @Inject constructor(
    private val credentialManager: CredentialManager,
    private val auth: FirebaseAuth
) {

    suspend fun signInWithGoogle(googleIdToken: String): FirebaseUser? {
        return try {
            val firebaseCredential = GoogleAuthProvider.getCredential(googleIdToken, null)
            auth.signInWithCredential(firebaseCredential).await().user
        } catch (e: Exception) {
            // TODO: 예외 처리
            null
        }
    }

    suspend fun signOutWithGoogle() {
        auth.signOut()
        credentialManager.clearCredentialState(
            ClearCredentialStateRequest()
        )
    }

    fun getCurrentUser(): FirebaseUser? {
        return auth.currentUser
    }
}