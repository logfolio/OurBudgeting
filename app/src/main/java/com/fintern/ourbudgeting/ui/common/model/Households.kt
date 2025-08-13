package com.fintern.ourbudgeting.ui.common.model

data class Household(
    val id: String = "",
    val assetType: List<String> = listOf("현금"),
    val currency: String = "KRW",
    val isPrivate: Boolean = true,
    val name: String = "우리집 가계부",
    val ownerId: String = ""
)
