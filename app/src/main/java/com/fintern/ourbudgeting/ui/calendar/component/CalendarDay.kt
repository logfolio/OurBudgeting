package com.fintern.ourbudgeting.ui.calendar.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.fintern.ourbudgeting.R
import com.fintern.ourbudgeting.data.calendar.TransactionWithId
import com.fintern.ourbudgeting.ui.calendar.component.config.CalendarDayConfig
import com.fintern.ourbudgeting.ui.common.model.TransactionType
import java.time.LocalDate

@Composable
fun CalendarDay(
    date: LocalDate,
    modifier: Modifier = Modifier,
    dayConfig: CalendarDayConfig = CalendarDayConfig.default(),
    transactions: List<TransactionWithId> = emptyList()
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
    transactions: List<TransactionWithId>,
) {
    val today = remember { LocalDate.now() }
    val currentDay = today.isEqual(date)
    val todayBackgroundColor = if (currentDay) Color(0xFF964BFF) else Color.Transparent
    val todayTextColor = if (currentDay) Color.White else dayConfig.textStyle.color

    val dailyTotal = remember(transactions) {
        transactions.sumOf {
            if (it.transaction.type == TransactionType.INCOME) it.transaction.amount else -it.transaction.amount
        }
    }

    val displayAmount = if (dailyTotal >= 0) stringResource(
        R.string.prefix_plus_amount,
        dailyTotal
    ) else dailyTotal.toString()
    val amountColor = if (dailyTotal >= 0) Color.Red else Color.Blue

    Column(
        modifier = modifier.size(48.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = date.dayOfMonth.toString(),
            modifier = Modifier
                .wrapContentSize()
                .background(
                    color = todayBackgroundColor,
                    shape = RoundedCornerShape(4.dp)
                ),
            textAlign = TextAlign.Center,
            style = dayConfig.textStyle.copy(color = todayTextColor)
        )

        if (transactions.isNotEmpty()) {
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