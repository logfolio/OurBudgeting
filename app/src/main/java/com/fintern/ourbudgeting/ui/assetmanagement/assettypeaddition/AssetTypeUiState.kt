package com.fintern.ourbudgeting.ui.assetmanagement.assettypeaddition

data class AssetTypeUiState(
    val input: String = "",
    val message: String = "",
    val isLoading: Boolean = false
)

sealed class AssetRepositoryException(message: String) : Exception(message) {
    object UserNotAuthenticated : AssetRepositoryException("로그인 안됨")
    class DatabaseError(cause: Throwable) : AssetRepositoryException("데이터베이스 오류: ${cause.message}")
}