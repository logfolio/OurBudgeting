package com.fintern.ourbudgeting.ui.calendar.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.LocalDate
import kotlinx.datetime.minus
import kotlinx.datetime.plus

@Composable
fun Calendar(
    selectedDate: LocalDate,
    modifier: Modifier = Modifier,
) {

    CalendarContent(
        selectedDate = selectedDate,
        modifier = modifier,
    )
}

@Composable
fun CalendarContent(
    selectedDate: LocalDate,
    modifier: Modifier = Modifier,
) {
    var currentMonth by remember {
        mutableStateOf(
            selectedDate.minus(
                selectedDate.day - 1,
                DateTimeUnit.DAY
            )
        )
    }

    Column(
        modifier = modifier.background(Color.White),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        CalendarHeader(
            month = currentMonth.month,
            year = currentMonth.year,
            modifier = Modifier,
            onPreviousClick = { currentMonth = currentMonth.minus(1, DateTimeUnit.MONTH) },
            onNextClick = { currentMonth = currentMonth.plus(1, DateTimeUnit.MONTH) }
        )
    }
}
