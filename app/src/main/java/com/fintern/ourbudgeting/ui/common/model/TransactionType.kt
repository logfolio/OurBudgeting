package com.fintern.ourbudgeting.ui.common.model

import androidx.annotation.StringRes
import com.fintern.ourbudgeting.R

enum class TransactionType(@StringRes val labelRes: Int) {
    EXPENSE(R.string.expense),
    INCOME(R.string.income)
}