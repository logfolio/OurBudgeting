package com.fintern.ourbudgeting.di

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.fintern.ourbudgeting.data.repository.HouseholdRepository
import com.fintern.ourbudgeting.data.repository.UserPreferencesRepository
import com.fintern.ourbudgeting.ui.user.UserViewModel
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UserModule {

    @Provides
    @Singleton
    fun provideUserPreferencesRepository(
        datastore: DataStore<Preferences>
    ): UserPreferencesRepository {
        return UserPreferencesRepository(datastore)
    }

    @Provides
    @Singleton
    fun provideUserViewModel(
        userPreferencesRepository: UserPreferencesRepository,
        householdRepository: HouseholdRepository
    ): UserViewModel {
        return UserViewModel(userPreferencesRepository, householdRepository)
    }
}