package com.fintern.ourbudgeting.ui.calendar

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.fintern.ourbudgeting.ui.calendar.component.config.CalendarDayLabelConfig
import com.fintern.ourbudgeting.ui.calendar.extensions.toKoreanString
import java.time.DayOfWeek
import java.time.LocalDate

@Composable
fun CalendarGrid(
    dayOfWeek: () -> List<DayOfWeek>,
    calendarDayLabelConfig: CalendarDayLabelConfig,
    modifier: Modifier = Modifier,
    dates: () -> List<LocalDate>,
    content: @Composable (LocalDate) -> Unit,
) {
    val displayDates = dates()
    val displayDayOfWeek = dayOfWeek()

    Column(modifier = modifier) {
        Row {
            displayDayOfWeek.forEach { day ->
                Text(
                    text = day.toKoreanString(),
                    modifier = Modifier
                        .weight(1f)
                        .size(48.dp),
                    color = if (day == DayOfWeek.SUNDAY) Color.Red else Color.Black,
                    style = calendarDayLabelConfig.textStyle
                )
            }
        }

        displayDates.chunked(7).forEach { week ->
            Row {
                week.forEach { date ->
                    Box(modifier = Modifier.weight(1f)) {
                        content(date)
                    }
                }
            }
        }
    }
}