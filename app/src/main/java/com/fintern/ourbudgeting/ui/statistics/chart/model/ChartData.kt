package com.fintern.ourbudgeting.ui.statistics.chart.model

data class ChartData(
    val totalValue: Float,
    val percentages: List<Float>,
    val formattedPercentages: List<String>,
    val angles: List<Float>
)