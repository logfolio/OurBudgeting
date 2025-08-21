package com.fintern.ourbudgeting.data.repository

import com.fintern.ourbudgeting.data.calendar.TransactionWithId
import com.fintern.ourbudgeting.ui.calendar.TransactionUiState
import com.fintern.ourbudgeting.ui.calendar.component.FilterType
import com.fintern.ourbudgeting.ui.save.TransactionSaveUiState
import kotlinx.coroutines.flow.Flow

interface TransactionRepository {
    fun getTransactions(
        householdId: String,
        filter: FilterType = FilterType.ALL
    ): Flow<TransactionUiState>
}

interface LatestTransactionRepository {
    fun getLatestTransactions(
        householdId: String,
    ): Flow<List<TransactionWithId>>
}

interface TransactionSaveRepository {
    suspend fun saveTransaction(
        householdId: String,
        uid: String,
        uiState: TransactionSaveUiState
    ): Result<Unit>
}