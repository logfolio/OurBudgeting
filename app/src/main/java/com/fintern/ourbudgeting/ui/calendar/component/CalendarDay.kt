package com.fintern.ourbudgeting.ui.calendar.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.util.fastFilter
import com.fintern.ourbudgeting.ui.calendar.CalendarTransactions
import com.fintern.ourbudgeting.ui.calendar.component.config.CalendarDayConfig
import com.fintern.ourbudgeting.ui.common.model.TransactionType
import java.time.LocalDate

@Composable
fun CalendarDay(
    date: LocalDate,
    modifier: Modifier = Modifier,
    dayConfig: CalendarDayConfig = CalendarDayConfig.default(),
    transactions: CalendarTransactions = CalendarTransactions()
) {
    CalendarDayContent(
        date = date,
        modifier = modifier,
        dayConfig = dayConfig,
        transactions = transactions
    )
}

@Composable
fun CalendarDayContent(
    date: LocalDate,
    modifier: Modifier = Modifier,
    dayConfig: CalendarDayConfig = CalendarDayConfig.default(),
    transactions: CalendarTransactions,
) {
    val today = remember { LocalDate.now() }
    val currentDay = today.isEqual(date)
    val todayBackgroundColor = if (currentDay) Color(0xFF964BFF) else Color.Transparent
    val todayTextColor = if (currentDay) Color.White else dayConfig.textStyle.color
    val currentDayTransactions =
        remember(transactions) { transactions.transactionList.fastFilter { it.date == date } }

    val dailyTotal = remember(currentDayTransactions) {
        currentDayTransactions.sumOf {
            if (it.type == TransactionType.INCOME) it.amount else -it.amount
        }
    }

    val displayAmount = if (dailyTotal >= 0) "+$dailyTotal" else dailyTotal.toString()
    val amountColor = if (dailyTotal >= 0) Color.Blue else Color.Red

    Column(
        modifier = modifier
            .size(48.dp)
            .background(
                todayBackgroundColor, shape = CircleShape
            ),
        horizontalAlignment = Alignment.CenterHorizontally,


        ) {
        Text(
            text = date.dayOfMonth.toString(),
            modifier = Modifier.wrapContentSize(),
            textAlign = TextAlign.Center,
            style = dayConfig.textStyle.copy(color = todayTextColor)
        )
        if (currentDayTransactions.isNotEmpty()) {
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = displayAmount,
                fontSize = 8.sp,
                style = dayConfig.textStyle.copy(color = amountColor),
                modifier = Modifier.wrapContentSize()
            )
        }
    }
}