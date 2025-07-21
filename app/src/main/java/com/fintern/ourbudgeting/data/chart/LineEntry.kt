package com.fintern.ourbudgeting.data.chart

data class LineEntry(
    val value: Float,
    val lineLabel: String,
)  : BaseEntry(
    y = value,
    label = lineLabel
)
