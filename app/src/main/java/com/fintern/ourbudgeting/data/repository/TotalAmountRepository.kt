package com.fintern.ourbudgeting.data.repository

import kotlinx.coroutines.flow.Flow

interface TotalAmountRepository {
    fun observeTotalAmount(householdId: String): Flow<Result<Long>>
}