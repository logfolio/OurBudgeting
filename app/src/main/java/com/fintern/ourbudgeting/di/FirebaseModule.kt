package com.fintern.ourbudgeting.di

import com.fintern.ourbudgeting.data.repository.TransactionRepository
import com.fintern.ourbudgeting.data.repository.TransactionRepositoryImpl
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object FirebaseModule {

    @Provides
    @Singleton
    fun provideFirestore(): FirebaseFirestore = FirebaseFirestore.getInstance()

    @Provides
    @Singleton
    fun provideFirebaseAuth(): FirebaseAuth {
        return FirebaseAuth.getInstance()
    }

    @Provides
    @Singleton
    fun provideTransactionRepository(
        firestore: FirebaseFirestore
    ): TransactionRepository  {
        return TransactionRepositoryImpl(firestore)
    }
}