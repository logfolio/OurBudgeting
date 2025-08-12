package com.fintern.ourbudgeting.ui.assetmanagement.assetedition

data class AssetEditUiState(
    val assetTypes: List<String> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null,
    val isUpdating: Boolean = false,
    val editingAsset: String? = null,
    val editText: String = ""
)