package com.fintern.ourbudgeting.data.calendar

data class CategoryItemData(
    val id: String,
    val amount: Long,
    val description: String,
    val date: String,
    val userName: String,
    val categoryId: String
)

data class CategoryDefinition(
    val id: String,
    val emoji: String,
    val displayName: String
)

data class CategoryList(
    val category: CategoryDefinition,
    val items: List<CategoryItemData>
)