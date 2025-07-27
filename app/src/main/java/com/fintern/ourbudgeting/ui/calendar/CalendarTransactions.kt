package com.fintern.ourbudgeting.ui.calendar

import com.fintern.ourbudgeting.ui.common.model.TransactionType
import java.time.LocalDate

interface CalendarTransaction {

    val date: LocalDate
    val transactionName: String
    val type: TransactionType
    val amount: Long
    val category: Category
}

data class Category(
    val name: String,
)

data class BasicCalendarTransaction(
    override val date: LocalDate,
    override val transactionName: String,
    override val type: TransactionType,
    override val amount: Long,
    override val category: Category
) : CalendarTransaction

data class CalendarTransactions(
    val transactionList: List<CalendarTransaction> = emptyList()
)