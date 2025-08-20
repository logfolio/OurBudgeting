package com.fintern.ourbudgeting.data.calendar

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

data class CategoryDefinition(
    val id: String,
    val emoji: String,
    val displayName: String
)

data class CategoryList(
    val category: CategoryDefinition,
    val items: List<TransactionWithId>
)

data class TransactionWithId(
    val id: String,
    val transaction: Transaction
)