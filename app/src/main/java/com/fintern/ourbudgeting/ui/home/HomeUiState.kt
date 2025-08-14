package com.fintern.ourbudgeting.ui.home

import android.net.Uri

data class HomeUiState(
    val isLoading: Boolean = true,
    val error: String? = null,
    val totalAssetText: String = "",
    val latestTransaction: List<LatestTransactionUi> = emptyList(),
    val exchangeRates: List<ExchangeRateUi> = emptyList(),
)

data class LatestTransactionUi(
    val content: String,
    val amountText: String,
    val imageUri: Uri? = null,
)

data class ExchangeRateUi(
    val countryName: String,
    val currencyCode: String,
    val rateText: String,
)