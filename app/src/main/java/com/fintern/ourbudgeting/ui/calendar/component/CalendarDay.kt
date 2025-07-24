package com.fintern.ourbudgeting.ui.calendar.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import com.fintern.ourbudgeting.ui.calendar.component.config.CalendarDayConfig
import java.time.LocalDate

@Composable
fun CalendarDay(
    date: LocalDate,
    modifier: Modifier = Modifier,
    dayConfig: CalendarDayConfig = CalendarDayConfig.default(),
) {
    CalendarDayContent(
        date = date,
        modifier = modifier,
        dayConfig = dayConfig
    )
}

@Composable
fun CalendarDayContent(
    date: LocalDate,
    modifier: Modifier = Modifier,
    dayConfig: CalendarDayConfig = CalendarDayConfig.default()
) {
    val today = remember { LocalDate.now() }
    val currentDay = today.isEqual(date)
    val todayBackgroundColor = if (currentDay) Color(0xFF964BFF) else Color.Transparent
    val todayTextColor = if (currentDay) Color.White else dayConfig.textStyle.color

    Column(
        modifier = modifier
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
    }
}