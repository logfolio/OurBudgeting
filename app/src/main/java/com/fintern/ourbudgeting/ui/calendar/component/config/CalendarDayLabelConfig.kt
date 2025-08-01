package com.fintern.ourbudgeting.ui.calendar.component.config

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp

data class CalendarDayLabelConfig(
    val textStyle: TextStyle,
) {
    companion object {
        fun default() = CalendarDayLabelConfig(
            textStyle = TextStyle(
                fontWeight = FontWeight.Normal,
                textAlign = TextAlign.Center,
                fontSize = 14.sp,
                color = Color.White
            )
        )
    }
}