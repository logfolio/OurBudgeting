package com.fintern.ourbudgeting.data.calendar

import com.fintern.ourbudgeting.ui.common.model.TransactionType
import com.google.firebase.Timestamp

data class CategoryItemData(
    val id: String,
    val amount: Long,
    val description: String,
    val date: Timestamp,
    val userName: String,
    val type: TransactionType,
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