package com.fintern.ourbudgeting.ui.save

import com.fintern.ourbudgeting.ui.common.model.TransactionType

data class AddTransactionUiState(
    val transactionType: TransactionType = TransactionType.EXPENSE,
    val selectedDate: Long? = null
)