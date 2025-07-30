package com.fintern.ourbudgeting.ui.assetmanagement.data

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

data class LineChartConfig(
    val data: List<Point> = emptyList(),
    val graphLineColor: Color,
    val pointColor: Color,
    val fillBrush: Boolean = true,
    val drawCircleOnTop: Boolean = true,
    val drawTextOnTop: Boolean = true,
    val pointRadius: Dp = 4.dp
)
