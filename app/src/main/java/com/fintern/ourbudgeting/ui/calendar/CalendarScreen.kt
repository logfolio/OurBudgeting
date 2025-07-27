package com.fintern.ourbudgeting.ui.calendar

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.fintern.ourbudgeting.R
import com.fintern.ourbudgeting.ui.calendar.component.Calendar
import com.fintern.ourbudgeting.ui.calendar.component.CalendarTopAppbar
import com.fintern.ourbudgeting.ui.common.model.TransactionType
import java.time.DayOfWeek
import java.time.LocalDate

@Preview
@Composable
fun CalendarScreen() {

    val sampleTransactions = CalendarTransactions(
        transactionList = listOf(
            BasicCalendarTransaction(
                date = LocalDate.now(),
                transactionName = "편의점",
                type = TransactionType.EXPENSE,
                amount = 3000L,
                category = Category(name = "식비")
            ),
            BasicCalendarTransaction(
                date = LocalDate.now(),
                transactionName = "월급",
                type = TransactionType.INCOME,
                amount = 2000000L,
                category = Category(name = "급여")
            ),
            BasicCalendarTransaction(
                date = LocalDate.now().minusDays(2),
                transactionName = "커피",
                type = TransactionType.EXPENSE,
                amount = 4500L,
                category = Category(name = "음료")
            )
        )
    )

    Scaffold(
        topBar = {
            CalendarTopAppbar(
                title = stringResource(R.string.calendar_label_app_name),
                onNotificationClick = {

                },
            )
        },
        content = { innerPadding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .background(Color.White)
            ) {
                Calendar(
                    startDayOfWeek = DayOfWeek.SUNDAY,
                    selectedDate = LocalDate.now(),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 24.dp),
                    transactions = sampleTransactions,
                )
            }
        }
    )
}
