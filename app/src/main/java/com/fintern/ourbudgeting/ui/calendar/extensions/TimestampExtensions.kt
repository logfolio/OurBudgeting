package com.fintern.ourbudgeting.ui.calendar.extensions

import com.google.firebase.Timestamp
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter

fun Timestamp.toLocalDate(): LocalDate {
    return this.toDate().toInstant()
        .atZone(ZoneId.systemDefault())
        .toLocalDate()
}

fun Timestamp.toFormatterDate(pattern: String = "yyyy-MM-dd"): String {
    val localDateTime = this.toLocalDate()
    return localDateTime.format(DateTimeFormatter.ofPattern(pattern))
}