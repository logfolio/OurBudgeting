package com.fintern.ourbudgeting.data.api

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.State
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: ExchangeRateRepository
) : ViewModel() {

    private val _exchangeRates = mutableStateOf<List<ExchangeRate>>(emptyList())
    val exchangeRates: State<List<ExchangeRate>> = _exchangeRates

    private val _isLoading = mutableStateOf(false)
    val isLoading: State<Boolean> = _isLoading

    private val _error = mutableStateOf<String?>(null)
    val error: State<String?> = _error

    fun loadExchangeRates(authKey: String, searchDate: String? = null) {
        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null

            repository.getExchangeRates(authKey, searchDate)
                .onSuccess { rates ->
                    _exchangeRates.value = rates.filter { it.curNm == "미국 달러" || it.curNm == "일본 옌"|| it.curNm == "태국 바트" }
                }
                .onFailure { exception ->
                    _error.value = exception.message
                }

            _isLoading.value = false
        }
    }
}
