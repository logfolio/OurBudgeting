package com.fintern.ourbudgeting.ui.statistics.chart

import androidx.annotation.StringRes
import com.fintern.ourbudgeting.R
import com.fintern.ourbudgeting.data.chart.PieEntry
import com.fintern.ourbudgeting.ui.common.model.TransactionType

data class StatisticsUiState(
    val currentYear: Int = 2025,
    val currentMonth: Int = 1,
    val chartData: List<PieEntry> = emptyList(),
    val isLoading: Boolean = false,
    val error: StatisticsError? = null,
    val currentType: TransactionType = TransactionType.EXPENSE,
)

sealed class StatisticsError(@StringRes val messageResId: Int) {
    object NetworkError : StatisticsError(R.string.error_network)
    object FirestoreError : StatisticsError(R.string.error_firestore)
    object UnknownError : StatisticsError(R.string.error_unknown)
}