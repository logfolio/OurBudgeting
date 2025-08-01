package com.fintern.ourbudgeting.ui.calendar.component.config

data class CalendarConfig(
    val calendarHeaderConfig: CalendarHeaderConfig = CalendarHeaderConfig.default(),
    val calendarDayLabelConfig: CalendarDayLabelConfig = CalendarDayLabelConfig.default(),
    val calendarDayConfig: CalendarDayConfig = CalendarDayConfig.default()
)
