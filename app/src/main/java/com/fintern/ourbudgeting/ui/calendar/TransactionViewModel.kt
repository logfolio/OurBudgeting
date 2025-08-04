package com.fintern.ourbudgeting.ui.calendar

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fintern.ourbudgeting.data.calendar.TransactionWithId
import com.fintern.ourbudgeting.data.repository.RemoteTransactionRepository
import com.fintern.ourbudgeting.ui.calendar.component.FilterType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TransactionViewModel @Inject constructor(
    private val repository: RemoteTransactionRepository
) : ViewModel() {

    private val _transactionsUiState =
        MutableStateFlow<TransactionUiState>(TransactionUiState.Loading)
    val transactionsUiState: StateFlow<TransactionUiState> = _transactionsUiState.asStateFlow()

    fun loadTransactions(
        householdId: String, filter: FilterType = FilterType.ALL
    ) {
        viewModelScope.launch {
            repository.getTransactions(householdId, filter)
                .collect { uiState ->
                    _transactionsUiState.value = uiState
                }
        }
    }
}

sealed class TransactionUiState {
    object Loading : TransactionUiState()
    data class Success(val data: List<TransactionWithId>) : TransactionUiState()
    data class Error(val exception: Throwable) : TransactionUiState()
}