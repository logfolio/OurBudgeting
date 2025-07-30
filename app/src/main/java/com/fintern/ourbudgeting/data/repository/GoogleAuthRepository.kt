package com.fintern.ourbudgeting.data.repository

import android.content.Context
import androidx.credentials.ClearCredentialStateRequest
import androidx.credentials.CredentialManager
import androidx.credentials.GetCredentialRequest
import com.fintern.ourbudgeting.BuildConfig
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class GoogleAuthRepository @Inject constructor(
    private val credentialManager: CredentialManager,
    private val auth: FirebaseAuth
) {

    suspend fun signInWithGoogle(context: Context): FirebaseUser? {
        val googleIdOption = GetGoogleIdOption.Builder()
            .setFilterByAuthorizedAccounts(false)
            .setServerClientId(BuildConfig.GOOGLE_CLIENT_ID)
            .build()

        val request = GetCredentialRequest.Builder()
            .addCredentialOption(googleIdOption)
            .build()

        return try {
            val result = credentialManager.getCredential(context, request)
            val credential = result.credential

            // GoogleIdTokenCredential을 사용하여 id를 추출하여 유효성 검사한 후, 서버에서 인증
            val googleIdTokenCredential = GoogleIdTokenCredential.createFrom(credential.data)
            val googleIdToken = googleIdTokenCredential.idToken

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