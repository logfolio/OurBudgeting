package com.fintern.ourbudgeting.di

import com.fintern.ourbudgeting.data.repository.StatisticsRepository
import com.google.firebase.firestore.FirebaseFirestore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object StatisticsModule {

    @Provides
    @Singleton
    fun provideStatisticsRepository(
        firestore: FirebaseFirestore
    ): StatisticsRepository {
        return StatisticsRepository(firestore)
    }
}