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
        // 구글 로그인 요청 인스턴스화
        // GetGoogleIdOption으로 사용자의 Google ID Token 검색
        val googleIdOption = GetGoogleIdOption.Builder()
            .setFilterByAuthorizedAccounts(false)
            .setServerClientId(BuildConfig.GOOGLE_CLIENT_ID)
            .build()

        // GetCredentialRequest 인스턴스화
        val request = GetCredentialRequest.Builder()
            .addCredentialOption(googleIdOption)
            .build()

        return try {
            val result = credentialManager.getCredential(context, request)
            val credential = result.credential

            // GoogleIdTokenCredential을 사용하여 id를 추출하여 유효성 검사한 후, 서버에서 인증
            val googleIdTokenCredential = GoogleIdTokenCredential.createFrom(credential.data)
            val googleIdToken = googleIdTokenCredential.idToken

            // 로그인 방법과 사용자의 고유 식별자
            val firebaseCredential = GoogleAuthProvider.getCredential(googleIdToken, null)

            // 사용자의 Firebase 인증
            auth.signInWithCredential(firebaseCredential).await().user

        } catch (e: Exception) {
            null
        }
    }

    suspend fun signOutWithGoogle() {
        auth.signOut()
        credentialManager.clearCredentialState(
            ClearCredentialStateRequest()
        )
    }
}