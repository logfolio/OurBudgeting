package com.fintern.ourbudgeting.ui.calendar.component.config

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp

data class CalendarHeaderConfig(
    val textStyle: TextStyle,
) {
    companion object {

        fun default() = CalendarHeaderConfig(
            textStyle = TextStyle(
                fontSize = 16.sp,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )
        )
    }
}
