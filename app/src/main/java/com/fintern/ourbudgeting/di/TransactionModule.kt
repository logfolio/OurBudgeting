package com.fintern.ourbudgeting.di

import com.fintern.ourbudgeting.data.repository.RemoteTransactionSaveRepository
import com.fintern.ourbudgeting.data.repository.TransactionSaveRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent

@Module
@InstallIn(ActivityComponent::class)
abstract class TransactionModule {

    @Binds
    abstract fun bindTransactionSaveRepository(
        impl: RemoteTransactionSaveRepository
    ): TransactionSaveRepository
}