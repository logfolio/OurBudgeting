package com.fintern.ourbudgeting.util

data class ReceiptParsedResult(
    val amount: String? = null,
    val storeName: String? = null,
)

object ReceiptOcrConfig {
    val AMOUNT_REGEX = Regex("""(\d{1,3}(,\d{3})+|\d+)([원₩])?""")
    val AMOUNT_UNIT_SYMBOLS = listOf(",", "₩", "원")
    val STORE_NAME_KEYWORDS = listOf("마트", "점", "대표")
}