package com.fintern.ourbudgeting.ui.save

import android.net.Uri
import com.fintern.ourbudgeting.ui.common.model.TransactionType

data class TransactionAddUiState(
    val transactionType: TransactionType = TransactionType.EXPENSE,
    val selectedDate: Long? = null,
    val selectedAsset: String? = null,
    val selectedCategory: String? = null,
    val amount: String = "",
    val content: String = "",
    val location: String = "",
    val photoUri: Uri? = null
)