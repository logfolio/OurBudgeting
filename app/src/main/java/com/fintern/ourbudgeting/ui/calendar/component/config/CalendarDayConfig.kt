package com.fintern.ourbudgeting.ui.calendar.component.config

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

data class CalendarDayConfig(
    val size: Dp,
    val textStyle: TextStyle,
) {
    companion object {

        fun default() = CalendarDayConfig(
            size = 56.dp,
            textStyle = TextStyle(
                fontSize = 14.sp,
                color = Color.Black
            ),
        )
    }
}