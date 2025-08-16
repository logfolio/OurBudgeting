package com.fintern.ourbudgeting.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fintern.ourbudgeting.data.repository.ExchangeRateRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: ExchangeRateRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    private val targetCurNames = setOf("미국 달러", "일본 옌", "태국 바트")

    fun loadExchangeRates(searchDate: String? = null) {
        viewModelScope.launch {
            setLoading()
            repository.getExchangeRates(searchDate).fold(
                onSuccess = { rates ->
                    val uiList = rates.asSequence()
                        .filter { it.curNm in targetCurNames }
                        .map { rate ->
                            ExchangeRateUi(
                                countryName = rate.curNm,
                                currencyCode = rate.curUnit,
                                rateText = rate.dealBasR,
                            )
                        }
                        .toList()

                    if (uiList.isEmpty()) {
                        setNoData()
                    } else {
                        setData(uiList)
                    }
                },
                onFailure = { e ->
                    setError(
                        when (e) {
                            is IOException -> HomeError.Network
                            else -> HomeError.Unknown
                        }
                    )
                }
            )
        }
    }

    private fun setLoading() = _uiState.update {
        it.copy(isLoading = true, error = null)
    }

    private fun setData(list: List<ExchangeRateUi>) = _uiState.update {
        it.copy(exchangeRates = list, isLoading = false, error = null)
    }

    private fun setNoData() = _uiState.update {
        it.copy(exchangeRates = emptyList(), isLoading = false, error = HomeError.NoData)
    }

    private fun setError(error: HomeError) = _uiState.update {
        it.copy(isLoading = false, error = error)
    }
}