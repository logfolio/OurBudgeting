package com.fintern.ourbudgeting.di

import com.fintern.ourbudgeting.data.repository.RemoteTransactionRepository
import com.fintern.ourbudgeting.data.repository.TransactionRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun bindTransactionRepository(
        remoteTransactionRepository: RemoteTransactionRepository
    ) : TransactionRepository
}