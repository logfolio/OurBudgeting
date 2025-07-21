package com.fintern.ourbudgeting.data.chart

import androidx.compose.ui.graphics.Color

data class PieEntry(
    val value: Float,
    val pieLabel: String,
    val pieColor: Color
) : BaseEntry(
    y = value,
    label = pieLabel
)