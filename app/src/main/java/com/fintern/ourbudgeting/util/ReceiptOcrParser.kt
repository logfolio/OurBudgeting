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

object ReceiptOcrParser {
    fun parseFields(text: String): ReceiptParsedResult {
        val lines = text.lines()

        return ReceiptParsedResult(
            amount = extractAmount(lines),
            storeName = extractStoreName(lines)
        )
    }

    private fun extractAmount(lines: List<String>): String? {
        return lines
            .asSequence()
            .filter { ReceiptOcrConfig.AMOUNT_REGEX.containsMatchIn(it) }
            .mapNotNull { ReceiptOcrConfig.AMOUNT_REGEX.find(it)?.value }
            .map { raw ->
                ReceiptOcrConfig.AMOUNT_UNIT_SYMBOLS.fold(raw) { acc, symbol ->
                    acc.replace(symbol, "")
                }.trim()
            }
            .mapNotNull { it.toIntOrNull() }
            .maxOrNull()
            ?.toString()
    }

    private fun extractStoreName(lines: List<String>): String? {
        return lines.firstOrNull { line ->
            ReceiptOcrConfig.STORE_NAME_KEYWORDS.any { keyword -> line.contains(keyword) }
        }?.trim()
    }
}