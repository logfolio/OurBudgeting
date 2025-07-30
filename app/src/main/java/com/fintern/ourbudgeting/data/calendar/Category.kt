package com.fintern.ourbudgeting.data.calendar

sealed interface CategoryItem {
    val id: String
    val amount: Int
    val description: String
    val date: String
    val userName: String
    val emoji: String
}

data class FoodItem(
    override val id: String,
    override val amount: Int,
    override val description: String,
    override val date: String,
    override val userName: String,
    override val emoji: String = ("🍔"),
) : CategoryItem

data class CategoryList(
    val name: String,
    val items: List<CategoryItem>
)