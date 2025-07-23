package com.fintern.ourbudgeting.ui.statistics.chart

import com.fintern.ourbudgeting.data.chart.PieEntry

data class StatisticsUiState(
    val currentYear: Int = 2025,
    val currentMonth: Int = 1,
    val chartData: List<PieEntry> = emptyList(),
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)