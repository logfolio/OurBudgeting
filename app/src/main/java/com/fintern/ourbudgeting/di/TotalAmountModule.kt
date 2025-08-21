package com.fintern.ourbudgeting.di

import com.fintern.ourbudgeting.data.repository.RemoteTotalAmountRepository
import com.fintern.ourbudgeting.data.repository.TotalAmountRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class TotalAmountModule {

    @Binds
    @Singleton
    abstract fun bindTotalAmountRepository(
        remoteTotalAmountRepository: RemoteTotalAmountRepository
    ): TotalAmountRepository
}