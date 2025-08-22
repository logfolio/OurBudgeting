package com.fintern.ourbudgeting.ui.save

import android.net.Uri
import androidx.annotation.StringRes
import androidx.compose.ui.text.input.TextFieldValue
import com.fintern.ourbudgeting.R
import com.fintern.ourbudgeting.ui.common.model.FirebaseError
import com.fintern.ourbudgeting.ui.common.model.TransactionType

data class TransactionSaveUiState(
    val isSaveEnabled: Boolean = false,
    val transactionType: TransactionType = TransactionType.EXPENSE,
    val selectedDate: Long? = null,
    val assetTypes: List<String> = emptyList(),
    val selectedAsset: String? = null,
    val selectedCategoryLabel: String? = null,
    val selectedCategoryCode: String? = null,
    val amountTextFieldValue: TextFieldValue = TextFieldValue(""),
    val amount: Long = 0L,
    val content: String = "",
    val location: String = "",
    val photoUri: Uri? = null,
    val isLoading: Boolean = false,
    val isSuccess: Boolean = false,
    val error: FirebaseError? = null,
    @param:StringRes val successMessageResId: Int = R.string.save_success,
)

sealed class TransactionUiEvent {
    data class ShowSnackbar(@param:StringRes val messageResId: Int) : TransactionUiEvent()
    object Success : TransactionUiEvent()
}