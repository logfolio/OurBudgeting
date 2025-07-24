package com.fintern.ourbudgeting.ui.calendar

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.fintern.ourbudgeting.ui.calendar.component.config.CalendarDayLabelConfig
import com.fintern.ourbudgeting.ui.calendar.extensions.toKoreanString
import java.time.DayOfWeek
import java.time.LocalDate

@Composable
fun CalendarScaffold(
    dayOfWeek: () -> List<DayOfWeek>,
    calendarDayLabelConfig: CalendarDayLabelConfig,
    modifier: Modifier = Modifier,
    dates: () -> List<LocalDate>,
    content: @Composable (LocalDate) -> Unit,
) {
    val displayDates = dates()
    val displayDayOfWeek = dayOfWeek()

    LazyVerticalGrid(
        modifier = modifier,
        columns = GridCells.Fixed(7),
        horizontalArrangement = Arrangement.Center,
        content = {
            items(displayDayOfWeek) { dayOfWeek ->
                Text(
                    text = dayOfWeek.toKoreanString(),
                    color = if (dayOfWeek == DayOfWeek.SUNDAY) Color.Red else Color.Black,
                    style = calendarDayLabelConfig.textStyle
                )
            }
            items(items = displayDates) { date ->
                content(date)
            }
        }
    )
}