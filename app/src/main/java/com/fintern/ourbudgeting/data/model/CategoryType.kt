package com.fintern.ourbudgeting.data.model

import androidx.compose.ui.graphics.Color
import com.fintern.ourbudgeting.R

enum class IncomeCategoryType(
    val labelRes: Int,
    val color: Color
) {
    SALARY(R.string.income_salary, Color(0xFF4CAF50)),
    BONUS(R.string.income_bonus, Color(0xFF81C784)),
    INVESTMENT(R.string.income_investment, Color(0xFF64B5F6)),
    ALLOWANCE(R.string.income_allowance, Color(0xFFFFB74D)),
    CASHBACK(R.string.income_cashback, Color(0xFFBA68C8)),
    ETC(R.string.income_etc, Color(0xFF9E9E9E))
}

enum class ExpenseCategoryType(
    val labelRes: Int,
    val color: Color
) {
    FOOD(R.string.expense_food, Color(0xFFE57373)),
    CAFE_SNACK(R.string.expense_cafe_snack, Color(0xFFFF8A65)),
    TRANSPORT(R.string.expense_transport, Color(0xFF64B5F6)),
    SHOPPING(R.string.expense_shopping, Color(0xFFFFB74D)),
    LIVING_SUPPLIES(R.string.expense_living_supplies, Color(0xFF4DD0E1)),
    MEDICAL(R.string.expense_medical, Color(0xFF81C784)),
    EDUCATION(R.string.expense_education, Color(0xFF9575CD)),
    CULTURE(R.string.expense_culture, Color(0xFFA1887F)),
    TRAVEL(R.string.expense_travel, Color(0xFFFFD54F)),
    SUBSCRIPTION(R.string.expense_subscription, Color(0xFF7986CB)),
    TAX(R.string.expense_tax, Color(0xFF90A4AE)),
    ETC(R.string.expense_etc, Color(0xFF9E9E9E))
}