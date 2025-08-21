package com.fintern.ourbudgeting.data.calendar

import com.fintern.ourbudgeting.R
import com.google.firebase.Timestamp

data class Transaction(
    val amount: Long = 0,
    val assetId: String = "",
    val category: String = "",
    val description: String = "",
    val date: Timestamp? = null,
    val createdBy: String = "",
    val createdAt: Timestamp = Timestamp.now(),
    val type: String? = null,
    val photoUrl: String? =  null,
)

data class CategoryList(
    val category: CategoryType,
    val items: List<TransactionWithId>
)

data class TransactionWithId(
    val id: String,
    val transaction: Transaction
)

enum class CategoryType(
    val labelRes: Int,
) {
    SALARY(R.string.income_salary),
    BONUS(R.string.income_bonus),
    INVESTMENT(R.string.income_investment),
    ALLOWANCE(R.string.income_allowance),
    CASHBACK(R.string.income_cashback),

    FOOD(R.string.expense_food),
    CAFE_SNACK(R.string.expense_cafe_snack),
    TRANSPORT(R.string.expense_transport),
    SHOPPING(R.string.expense_shopping),
    LIVING_SUPPLIES(R.string.expense_living_supplies),
    MEDICAL(R.string.expense_medical),
    EDUCATION(R.string.expense_education),
    CULTURE(R.string.expense_culture),
    TRAVEL(R.string.expense_travel),
    SUBSCRIPTION(R.string.expense_subscription),
    TAX(R.string.expense_tax),
    ETC(R.string.expense_etc)
}