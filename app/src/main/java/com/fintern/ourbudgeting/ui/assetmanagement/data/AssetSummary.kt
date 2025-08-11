package com.fintern.ourbudgeting.ui.assetmanagement.data

data class AssetSummary(
    val totalAsset: Long = 0L,
    val totalDebt: Long = 0L,
    val netWorth: Long = 0L,
    val assetDetails: List<AssetDetail> = emptyList()
)

data class AssetDetail(
    val assetName: String = "",
    val totalAmount: Long = 0L,
    val incomeAmount: Long = 0L,
    val expenseAmount: Long = 0L
)
