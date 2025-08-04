package com.fintern.ourbudgeting.util

import java.text.NumberFormat
import java.util.Locale

object NumberUtils {
    fun formatAmount(value: Long): String {
        return NumberFormat.getNumberInstance(Locale.KOREA).format(value)
    }

    fun extractDigits(input: String): Long {
        val cleaned = input.replace("\\D".toRegex(), "")
        val trimmed = if (cleaned.length > 19) cleaned.substring(0, 19) else cleaned
        return trimmed.toLongOrNull() ?: 0L
    }
}