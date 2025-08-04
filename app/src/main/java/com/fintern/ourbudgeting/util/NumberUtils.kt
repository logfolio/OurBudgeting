package com.fintern.ourbudgeting.util

import java.text.NumberFormat
import java.util.Locale

object NumberUtils {
    fun formatAmount(value: Long): String {
        return NumberFormat.getNumberInstance(Locale.KOREA).format(value)
    }

    fun extractDigits(input: String): Long {
        val cleaned = input.replace("\\D".toRegex(), "")
        return if (cleaned.isEmpty()) 0L else cleaned.toLong()
    }
}