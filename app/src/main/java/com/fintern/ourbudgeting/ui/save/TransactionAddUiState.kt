package com.fintern.ourbudgeting.ui.save

import android.net.Uri
import androidx.compose.ui.text.input.TextFieldValue
import com.fintern.ourbudgeting.ui.common.model.TransactionType

data class TransactionAddUiState(
    val isSaveEnabled: Boolean = false,
    val transactionType: TransactionType = TransactionType.EXPENSE,
    val selectedDate: Long? = null,
    val selectedAsset: String? = null,
    val selectedCategory: String? = null,
    val amountTextFieldValue: TextFieldValue = TextFieldValue(""),
    val amount: Long = 0L,
    val content: String = "",
    val location: String = "",
    val photoUri: Uri? = null
)