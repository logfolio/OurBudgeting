package com.fintern.ourbudgeting.ui.statistics.chart.model

import androidx.annotation.StringRes
import com.fintern.ourbudgeting.R
import com.fintern.ourbudgeting.ui.common.model.TransactionType

enum class StatisticsTab(
    val type: TransactionType,
    @StringRes val labelResId: Int
) {
    EXPENSE(TransactionType.EXPENSE, R.string.expense),
    INCOME(TransactionType.INCOME, R.string.income),
}