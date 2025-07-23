package com.fintern.ourbudgeting.ui.calendar.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.fintern.ourbudgeting.ui.calendar.CalendarScaffold
import com.fintern.ourbudgeting.ui.calendar.component.config.CalendarConfig
import com.fintern.ourbudgeting.ui.calendar.component.config.CalendarDayLabelConfig
import java.time.DayOfWeek
import java.time.LocalDate

@Composable
fun Calendar(
    selectedDate: LocalDate,
    modifier: Modifier = Modifier,
    calendarConfig: CalendarConfig = CalendarConfig(),
    startDayOfWeek: DayOfWeek = DayOfWeek.SUNDAY
) {

    CalendarContent(
        selectedDate = selectedDate,
        modifier = modifier,
        startDayOfWeek = startDayOfWeek,
        dayConfig = calendarConfig.calendarDayLabelConfig
    )
}

@Composable
fun CalendarContent(
    selectedDate: LocalDate,
    modifier: Modifier = Modifier,
    startDayOfWeek: DayOfWeek,
    dayConfig: CalendarDayLabelConfig,
) {
    var currentMonth by remember {
        mutableStateOf(
            selectedDate.withDayOfMonth(1)
        )
    }

    val daysOfWeek = DayOfWeek.entries.let {
        it.drop(startDayOfWeek.ordinal) + it.take(startDayOfWeek.ordinal)
    }

    Column(
        modifier = modifier.background(Color.White),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        CalendarHeader(
            month = currentMonth.month,
            year = currentMonth.year,
            modifier = Modifier,
            onPreviousClick = { currentMonth = currentMonth.minusMonths(1) },
            onNextClick = { currentMonth = currentMonth.plusMonths(1) }
        )
        CalendarScaffold(
            modifier = Modifier.fillMaxWidth(),
            dayOfWeek = { daysOfWeek },
            calendarDayLabelConfig = dayConfig,
        )
    }
}
