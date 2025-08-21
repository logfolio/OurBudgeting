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
import com.fintern.ourbudgeting.util.ReceiptOcrParser
import com.google.firebase.FirebaseNetworkException
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.storage.StorageException
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TransactionSaveViewModel @Inject constructor(
    private val repository: TransactionSaveRepository,
) : ViewModel() {
    private val _uiState = MutableStateFlow(TransactionSaveUiState())
    val uiState: StateFlow<TransactionSaveUiState> = _uiState

    private val _eventFlow = MutableSharedFlow<TransactionUiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    fun saveTransaction(householdId: String, uid: String) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(
                isLoading = true
            )
            repository.saveTransaction(
                householdId = householdId,
                uid = uid,
                uiState = _uiState.value
            ).onSuccess {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    isSuccess = true,
                )
                _eventFlow.emit(TransactionUiEvent.Success)
            }.onFailure { e ->
                val error = when (e) {
                    is FirebaseNetworkException -> FirebaseError.NetworkError
                    is FirebaseFirestoreException -> FirebaseError.FirestoreError
                    is StorageException -> FirebaseError.ImageUploadError
                    else -> FirebaseError.UnknownError
                }

                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    error = error
                )

                _eventFlow.emit(TransactionUiEvent.ShowSnackbar(error.messageResId))
            }
        }
    }

    fun applyScannedReceipt(text: String) {
        val parsed = ReceiptOcrParser.parseFields(text)

        parsed.amount?.let {
            setAmountTextFieldValue(TextFieldValue(it))
        }
        parsed.storeName?.let {
            setContent(it)
        }
    }

    private fun updateSaveEnabledState() {
        val state = _uiState.value
        val isValid = state.selectedDate != null &&
                !state.selectedAsset.isNullOrBlank() &&
                state.selectedCategoryCode != null &&
                state.selectedCategoryLabel != null &&
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

    fun setCategory(label: String, code: String) {
        _uiState.update {
            it.copy(
                selectedCategoryLabel = label,
                selectedCategoryCode = code
            )
        }
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