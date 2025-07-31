package com.fintern.ourbudgeting.ui.statistics.chart.model

import androidx.compose.ui.unit.Dp

data class ChartConfig(
    val chartRadiusPx: Float,
    val totalCanvasSize: Dp,
    val maxTextWidth: Dp,
    val textPadding: Dp,
    val lineExtension: Dp
)