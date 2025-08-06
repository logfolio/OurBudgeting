package com.fintern.ourbudgeting.ui.calendar.extensions

import java.time.LocalDate

fun LocalDate.onDayClick(
    onClickedNewDate: (LocalDate) -> Unit,
) {
    onClickedNewDate(this)
}
