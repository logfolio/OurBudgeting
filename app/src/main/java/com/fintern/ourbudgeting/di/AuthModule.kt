package com.fintern.ourbudgeting.di

import android.content.Context
import androidx.credentials.CredentialManager
import com.fintern.ourbudgeting.data.auth.GoogleSignInClient
import com.fintern.ourbudgeting.data.repository.GoogleAuthRepository
import com.google.firebase.auth.FirebaseAuth
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AuthModule {

    @Provides
    @Singleton
    fun provideCredentialManager(
        @ApplicationContext context: Context
    ): CredentialManager {
        return CredentialManager.create(context)
    }

    @Provides
    @Singleton
    fun provideGoogleSignInClient(
        @ApplicationContext context: Context,
        credentialManager: CredentialManager
    ): GoogleSignInClient {
        return GoogleSignInClient(context, credentialManager)
    }

    @Provides
    @Singleton
    fun provideGoogleAuthRepository(
        firebaseAuth: FirebaseAuth
    ): GoogleAuthRepository {
        return GoogleAuthRepository(firebaseAuth)
    }
}