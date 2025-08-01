package com.fintern.ourbudgeting.ui.save

import androidx.lifecycle.ViewModel
import com.fintern.ourbudgeting.ui.common.model.TransactionType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class AddTransactionViewModel @Inject constructor() : ViewModel() {
    private val _uiState = MutableStateFlow(AddTransactionUiState())
    val uiState: StateFlow<AddTransactionUiState> = _uiState

    fun setTransactionType(type: TransactionType) {
        _uiState.update { it.copy(transactionType = type) }
    }

    fun setSelectedDate(date: Long?) {
        _uiState.update { it.copy(selectedDate = date) }
    }

    fun setSelectedAsset(asset: String) {
        _uiState.update { it.copy(selectedAsset = asset) }
    }

    fun setCategory(category: String) {
        _uiState.update { it.copy(selectedCategory = category) }
    }

    fun setAmountText(input: String) {
        _uiState.update {
            it.copy(amount = input)
        }
    }
}
