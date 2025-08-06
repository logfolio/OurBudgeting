package com.fintern.ourbudgeting.ui.save

import android.net.Uri
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fintern.ourbudgeting.data.repository.TransactionSaveRepository
import com.fintern.ourbudgeting.ui.common.model.FirebaseError
import com.fintern.ourbudgeting.ui.common.model.TransactionType
import com.fintern.ourbudgeting.util.NumberUtils
import com.google.firebase.FirebaseNetworkException
import com.google.firebase.firestore.FirebaseFirestoreException
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TransactionAddViewModel @Inject constructor(
    private val repository: TransactionSaveRepository,
) : ViewModel() {
    private val _uiState = MutableStateFlow(TransactionAddUiState())
    val uiState: StateFlow<TransactionAddUiState> = _uiState

    fun saveTransaction(householdId: String) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(
                isLoading = true
            )
            repository.saveTransaction(
                householdId = householdId,
                uid = "",
                uiState = _uiState.value
            ).onSuccess {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    isSuccess = true,
                )
            }.onFailure { e ->

                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    error = when (e) {
                        is FirebaseNetworkException -> FirebaseError.NetworkError
                        is FirebaseFirestoreException -> FirebaseError.FirestoreError
                        else -> FirebaseError.UnknownError
                    }
                )
            }
        }
    }

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
        val number = NumberUtils.extractDigits(textFieldValue.text)
        val formattedAmount = NumberUtils.formatAmount(number)

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