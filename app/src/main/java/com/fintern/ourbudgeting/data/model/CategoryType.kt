package com.fintern.ourbudgeting.data.model

import androidx.compose.ui.graphics.Color

enum class IncomeCategoryType(val color: Color) {
    SALARY(Color(0xFF4CAF50)),
    BONUS(Color(0xFF81C784)),
    INVESTMENT(Color(0xFF64B5F6)),
    ALLOWANCE(Color(0xFFFFB74D)),
    CASHBACK(Color(0xFFBA68C8)),
    ETC(Color(0xFF9E9E9E))
}

enum class ExpenseCategoryType(val color: Color) {
    FOOD(Color(0xFFE57373)),
    CAFE_SNACK(Color(0xFFFF8A65)),
    TRANSPORT(Color(0xFF64B5F6)),
    SHOPPING(Color(0xFFFFB74D)),
    LIVING_SUPPLIES(Color(0xFF4DD0E1)),
    MEDICAL(Color(0xFF81C784)),
    EDUCATION(Color(0xFF9575CD)),
    CULTURE(Color(0xFFA1887F)),
    TRAVEL(Color(0xFFFFD54F)),
    SUBSCRIPTION(Color(0xFF7986CB)),
    TAX(Color(0xFF90A4AE)),
    ETC(Color(0xFF9E9E9E))
}