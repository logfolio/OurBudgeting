package com.fintern.ourbudgeting.data.repository

import com.fintern.ourbudgeting.data.calendar.TransactionWithId
import com.fintern.ourbudgeting.ui.calendar.UiState
import com.fintern.ourbudgeting.ui.calendar.component.FilterType
import kotlinx.coroutines.flow.Flow

interface TransactionRepository {
    fun getTransactions(
        householdId: String,
        filter: FilterType = FilterType.ALL
    ) : Flow<UiState<List<TransactionWithId>>>
}