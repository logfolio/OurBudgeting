package com.fintern.ourbudgeting.ui.calendar

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fintern.ourbudgeting.data.calendar.TransactionWithId
import com.fintern.ourbudgeting.data.repository.TransactionRepositoryImpl
import com.fintern.ourbudgeting.ui.calendar.component.FilterType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TransactionViewModel @Inject constructor(
    private val repository: TransactionRepositoryImpl
) : ViewModel() {

    private val _transactionsUiState =
        MutableStateFlow<UiState<List<TransactionWithId>>>(UiState.Loading)
    val transactionsUiState: StateFlow<UiState<List<TransactionWithId>>> = _transactionsUiState

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

sealed class UiState<out T> {
    object Loading : UiState<Nothing>()
    data class Success<T>(val data: T) : UiState<T>()
    data class Error(val exception: Throwable) : UiState<Nothing>()
}