package com.fintern.ourbudgeting.ui.home

import android.net.Uri
import androidx.annotation.StringRes
import com.fintern.ourbudgeting.R

data class HomeUiState(
    val isLoading: Boolean = true,
    val error: HomeError? = null,
    val totalAssetText: Long = 0L,
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

sealed class HomeError(
    @param:StringRes val messageResId: Int,
) {
    object Network : HomeError(R.string.error_network)
    object NoData : HomeError(R.string.error_exchange_no_data)
    object Unknown : HomeError(R.string.error_unknown)
}