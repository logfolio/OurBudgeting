package com.fintern.ourbudgeting.ui.save

import android.net.Uri
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.ViewModel
import com.fintern.ourbudgeting.ui.common.model.TransactionType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import java.text.NumberFormat
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class TransactionAddViewModel @Inject constructor() : ViewModel() {
    private val _uiState = MutableStateFlow(TransactionAddUiState())
    val uiState: StateFlow<TransactionAddUiState> = _uiState

    private fun updateSaveEnabledState() {
        val state = _uiState.value
        val isValid = state.selectedDate != null &&
                !state.selectedAsset.isNullOrBlank() &&
                !state.selectedCategory.isNullOrBlank() &&
                state.amountTextFieldValue.text.isNotBlank() &&
                state.content.isNotBlank()

        _uiState.update { it.copy(isSaveEnabled = isValid) }
    }


    fun setTransactionType(type: TransactionType) {
        _uiState.update { it.copy(transactionType = type) }
    }

    fun setSelectedDate(date: Long?) {
        _uiState.update { it.copy(selectedDate = date) }
        updateSaveEnabledState()
    }

    fun setSelectedAsset(asset: String) {
        _uiState.update { it.copy(selectedAsset = asset) }
        updateSaveEnabledState()
    }

    fun setCategory(category: String) {
        _uiState.update { it.copy(selectedCategory = category) }
        updateSaveEnabledState()
    }

    fun setAmountTextFieldValue(textFieldValue: TextFieldValue) {
        val cleanedInput = textFieldValue.text.replace("\\D".toRegex(), "")

        val number = if (cleanedInput.isEmpty()) 0L else cleanedInput.toLong()
        val formattedAmount = NumberFormat.getNumberInstance(Locale.US).format(number)

        val newTextFieldValue = textFieldValue.copy(
            text = formattedAmount,
            selection = TextRange(formattedAmount.length)
        )

        _uiState.update {
            it.copy(
                amountTextFieldValue = newTextFieldValue,
                amount = number
            )
        }
        updateSaveEnabledState()
    }

    fun setContent(content: String) {
        _uiState.update { it.copy(content = content) }
        updateSaveEnabledState()
    }

    fun setPhotoUri(uri: Uri?) {
        _uiState.update { it.copy(photoUri = uri) }
    }

    fun clearPhotoUri() {
        _uiState.update { it.copy(photoUri = null) }
    }
}