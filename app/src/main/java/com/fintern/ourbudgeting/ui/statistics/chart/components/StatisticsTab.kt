package com.fintern.ourbudgeting.ui.statistics.chart.components

import com.fintern.ourbudgeting.ui.common.model.TransactionType

enum class StatisticsTab(
    val type: TransactionType,
    val label: String
) {
    EXPENSE(TransactionType.EXPENSE, "지출"),
    INCOME(TransactionType.INCOME, "수입"),
}