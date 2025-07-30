package com.fintern.ourbudgeting.ui.calendar.extensions

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.fintern.ourbudgeting.R
import java.time.DayOfWeek

@Composable
fun DayOfWeek.toKoreanString(): String {
    return when (this) {
        DayOfWeek.SUNDAY -> stringResource(R.string.label_sunday)
        DayOfWeek.MONDAY -> stringResource(R.string.label_monday)
        DayOfWeek.TUESDAY -> stringResource(R.string.label_tuesday)
        DayOfWeek.WEDNESDAY -> stringResource(R.string.label_wednesday)
        DayOfWeek.THURSDAY -> stringResource(R.string.label_thursday)
        DayOfWeek.FRIDAY -> stringResource(R.string.label_friday)
        DayOfWeek.SATURDAY -> stringResource(R.string.label_saturday)
    }
}