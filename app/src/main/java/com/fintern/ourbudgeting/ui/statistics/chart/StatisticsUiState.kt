package com.fintern.ourbudgeting.ui.statistics.chart

import com.fintern.ourbudgeting.data.chart.PieEntry
import com.fintern.ourbudgeting.ui.statistics.chart.model.TransactionType

data class StatisticsUiState(
    val currentYear: Int = 2025,
    val currentMonth: Int = 1,
    val chartData: List<PieEntry> = emptyList(),
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val currentType: TransactionType = TransactionType.EXPENSE,
)