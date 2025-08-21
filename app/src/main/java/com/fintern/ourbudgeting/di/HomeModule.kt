package com.fintern.ourbudgeting.di

import com.fintern.ourbudgeting.data.repository.LatestTransactionRepository
import com.fintern.ourbudgeting.data.repository.RemoteLatestTransactionRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class HomeModule {

    @Binds
    abstract fun bindLatestTransactionRepository(
        remoteLatestTransactionRepository: RemoteLatestTransactionRepository
    ): LatestTransactionRepository
}