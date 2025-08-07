package com.fintern.ourbudgeting.data.repository

import com.fintern.ourbudgeting.ui.calendar.TransactionUiState
import com.fintern.ourbudgeting.ui.calendar.component.FilterType
import kotlinx.coroutines.flow.Flow

interface TransactionRepository {
    fun getTransactions(
        householdId: String,
        filter: FilterType = FilterType.ALL
    ) : Flow<TransactionUiState>
}