package com.fintern.ourbudgeting.ui.assetmanagement.data

import com.google.firebase.Timestamp

data class AssetTransaction(
    val id: String = "",
    val assetId: String = "",
    val category: String = "",
    val createdBy: String = "",
    val amount: Long = 0,
    val description: String = "",
    val type: String = "EXPENSE",
    val date: Timestamp = Timestamp.now()
)