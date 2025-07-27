package com.fintern.ourbudgeting.ui.assetmanagement.data

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

data class LineChartStyleConfig(
    val spacing: Float = 50f,
    val xAxisLabelColor: Color = Color.Black,
    val xAxisLabelTextSize: TextUnit = 11.sp,
    val yAxisLabelColor: Color = Color.Black,
    val yAxisLabelTextSize: TextUnit = 11.sp,
    val dataValueTextColor: Color = Color.DarkGray,
    val dataValueTextSize: TextUnit = 10.sp,
    val yAxisStep: Int = 1,
    val gridLineColor: Color = Color.LightGray,
    val gridStrokeWidth: Dp = 1.dp,
    val graphStrokeWidth: Dp = 2.dp,
    val graphStrokeCap: StrokeCap = StrokeCap.Butt,
    val showGridLines: Boolean = true
)
