package com.fintern.ourbudgeting.ui.calendar.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import com.fintern.ourbudgeting.ui.calendar.CalendarGrid
import com.fintern.ourbudgeting.ui.calendar.component.config.CalendarConfig
import com.fintern.ourbudgeting.ui.calendar.component.config.CalendarDayConfig
import com.fintern.ourbudgeting.ui.calendar.component.config.CalendarDayLabelConfig
import com.fintern.ourbudgeting.ui.calendar.extensions.onDayClick
import com.fintern.ourbudgeting.ui.calendar.extensions.toLocalDate
import com.fintern.ourbudgeting.util.CalendarConstants
import java.time.DayOfWeek
import java.time.LocalDate

@Composable
fun Calendar(
    modifier: Modifier = Modifier,
    selectedDate: LocalDate?,
    onDateClick: (LocalDate) -> Unit,
    calendarConfig: CalendarConfig = CalendarConfig(),
    startDayOfWeek: DayOfWeek = DayOfWeek.SUNDAY,
    categoryLists: List<CategoryList> = emptyList(),
    currentMonth: LocalDate,
    onPreviousClick: () -> Unit,
    onNextClick: () -> Unit,
) {
    CalendarContent(
        selectedDate = selectedDate,
        modifier = modifier,
        startDayOfWeek = startDayOfWeek,
        dayLabelConfig = calendarConfig.calendarDayLabelConfig,
        dayConfig = calendarConfig.calendarDayConfig,
        categoryLists = categoryLists,
        onDateClick = onDateClick,
        currentMonth = currentMonth,
        onPreviousClick = onPreviousClick,
        onNextClick = onNextClick,
    )
}

@Composable
fun CalendarContent(
    selectedDate: LocalDate?,
    modifier: Modifier = Modifier,
    startDayOfWeek: DayOfWeek,
    dayLabelConfig: CalendarDayLabelConfig,
    dayConfig: CalendarDayConfig,
    categoryLists: List<CategoryList>,
    onDateClick: (LocalDate) -> Unit,
    currentMonth: LocalDate,
    onPreviousClick: () -> Unit,
    onNextClick: () -> Unit,
) {

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
            onPreviousClick = onPreviousClick,
            onNextClick = onNextClick
        )
        CalendarGrid(
            modifier = Modifier.fillMaxWidth(),
            dayOfWeek = { daysOfWeek },
            calendarDayLabelConfig = dayLabelConfig,
            dates = { displayDates },
        ) { date ->

            val filteredItems = categoryLists.flatMap { it.items }.filter {
                val itemDate = it.transaction.date?.toLocalDate()
                itemDate == date
            }

            val clickableModifier = Modifier.clickable {
                date.onDayClick(onClickedNewDate = onDateClick)
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
                    },
                    selectedDate = selectedDate,
                    modifier = clickableModifier
                )
            } else {
                CalendarDay(
                    date = date,
                    transactions = filteredItems,
                    dayConfig = dayConfig.copy(
                        textStyle = dayConfig.textStyle
                            .copy(color = Color.LightGray)
                    ),
                    selectedDate = selectedDate
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

    val firstDayOffset =
        (firstDayOfMonth.dayOfWeek.ordinal - startDayOfWeek.ordinal + CalendarConstants.DAYS_IN_WEEK) % CalendarConstants.DAYS_IN_WEEK
    val calendarStartDate = firstDayOfMonth.minusDays(firstDayOffset.toLong())

    return (0 until CalendarConstants.CALENDAR_TOTAL_CELL_COUNT).map { i ->
        calendarStartDate.plusDays(i.toLong())
    }
}
