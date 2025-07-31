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
import com.fintern.ourbudgeting.data.calendar.CategoryList
import com.fintern.ourbudgeting.ui.calendar.CalendarScaffold
import com.fintern.ourbudgeting.ui.calendar.component.config.CalendarConfig
import com.fintern.ourbudgeting.ui.calendar.component.config.CalendarDayConfig
import com.fintern.ourbudgeting.ui.calendar.component.config.CalendarDayLabelConfig
import com.fintern.ourbudgeting.ui.calendar.extensions.toLocalDate
import java.time.DayOfWeek
import java.time.LocalDate

@Composable
fun Calendar(
    selectedDate: LocalDate,
    modifier: Modifier = Modifier,
    calendarConfig: CalendarConfig = CalendarConfig(),
    startDayOfWeek: DayOfWeek = DayOfWeek.SUNDAY,
    categoryLists: List<CategoryList> = emptyList(),
) {
    CalendarContent(
        selectedDate = selectedDate,
        modifier = modifier,
        startDayOfWeek = startDayOfWeek,
        dayLabelConfig = calendarConfig.calendarDayLabelConfig,
        dayConfig = calendarConfig.calendarDayConfig,
        categoryLists = categoryLists
    )
}

@Composable
fun CalendarContent(
    selectedDate: LocalDate,
    modifier: Modifier = Modifier,
    startDayOfWeek: DayOfWeek,
    dayLabelConfig: CalendarDayLabelConfig,
    dayConfig: CalendarDayConfig,
    categoryLists: List<CategoryList>
) {
    var currentMonth by remember {
        mutableStateOf(
            selectedDate.withDayOfMonth(1)
        )
    }

    val daysOfWeek = DayOfWeek.entries.let {
        it.drop(startDayOfWeek.ordinal) + it.take(startDayOfWeek.ordinal)
    }

    val displayDates by remember(currentMonth, startDayOfWeek) {
        mutableStateOf(getMonthDates(currentMonth, startDayOfWeek))
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
            calendarDayLabelConfig = dayLabelConfig,
            dates = { displayDates },
        ) { date ->

            val filteredItems = categoryLists.flatMap { it.items }.filter {
                val itemDate = it.date.toLocalDate()
                itemDate == date
            }

            if (date.month == currentMonth.month) {

                CalendarDay(
                    date = date,
                    transactions = filteredItems,
                    dayConfig = if (date.dayOfWeek == DayOfWeek.SUNDAY) {
                        dayConfig.copy(
                            textStyle = dayConfig.textStyle.copy(
                                color = Color.Red
                            )
                        )
                    } else {
                        dayConfig.copy(
                            textStyle = dayConfig.textStyle
                        )
                    }
                )
            } else {
                CalendarDay(
                    date = date,
                    transactions = filteredItems,
                    dayConfig = dayConfig.copy(
                        textStyle = dayConfig.textStyle
                            .copy(color = Color.LightGray)
                    )
                )
            }
        }
    }
}


fun getMonthDates(
    currentMonth: LocalDate,
    startDayOfWeek: DayOfWeek
): List<LocalDate> {
    val firstDayOfMonth = currentMonth.withDayOfMonth(1)

    val firstDayOffset = (firstDayOfMonth.dayOfWeek.ordinal - startDayOfWeek.ordinal + 7) % 7
    val calendarStartDate = firstDayOfMonth.minusDays(firstDayOffset.toLong())

    return (0 until 42).map { i ->
        calendarStartDate.plusDays(i.toLong())
    }
}
