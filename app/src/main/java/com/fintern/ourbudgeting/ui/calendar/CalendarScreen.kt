package com.fintern.ourbudgeting.ui.calendar

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.fintern.ourbudgeting.ui.calendar.component.Calendar
import java.time.LocalDate

@Composable
fun CalendarScreen() {

    Column(modifier = Modifier.wrapContentSize().background(Color.LightGray)) {
        Calendar(
            selectedDate = LocalDate.now(),
            modifier = Modifier.fillMaxWidth(),
        )
    }
}
