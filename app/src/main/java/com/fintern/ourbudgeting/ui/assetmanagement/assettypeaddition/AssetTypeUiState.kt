package com.fintern.ourbudgeting.ui.assetmanagement.assettypeaddition

import androidx.annotation.StringRes
import com.fintern.ourbudgeting.R

data class AssetTypeUiState(
    val input: String = "",
    val message: AssetViewModelMessage? = null,
    val isLoading: Boolean = false
)

sealed class AssetRepositoryException(@StringRes val messageResId: Int) :
    Exception(messageResId.toString()) {
    object UserNotAuthenticated : AssetRepositoryException(R.string.login_is_not_success)
    class DatabaseError(cause: Throwable) :
        AssetRepositoryException(R.string.database_error_occurred)
}

sealed class AssetViewModelMessage(@StringRes val messageResId: Int) {
    object EmptyInput : AssetViewModelMessage(R.string.empty_input_message)
    object AssetTypeAdded : AssetViewModelMessage(R.string.asset_type_added)
    object SaveFailed : AssetViewModelMessage(R.string.failed_to_save)
}