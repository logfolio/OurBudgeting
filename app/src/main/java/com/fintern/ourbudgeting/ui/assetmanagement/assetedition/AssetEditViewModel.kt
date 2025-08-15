package com.fintern.ourbudgeting.ui.assetmanagement.assetedition

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fintern.ourbudgeting.data.repository.AssetEditRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class AssetEditViewModel @Inject constructor(
    private val repository: AssetEditRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(AssetEditUiState())
    val uiState: StateFlow<AssetEditUiState> = _uiState.asStateFlow()

    fun observeAndGetAssetTypes(householdId: String) {
        viewModelScope.launch {
            repository.getAssetTypesFlow(householdId)
                .collect { assetTypes ->
                    _uiState.value = _uiState.value.copy(
                        assetTypes = assetTypes,
                        isLoading = false
                    )
                }
        }
    }

    fun startEditing(assetName: String) {
        _uiState.value = _uiState.value.copy(
            editingAsset = assetName,
            editText = assetName
        )
    }

    fun updateEditText(text: String) {
        _uiState.value = _uiState.value.copy(editText = text)
    }

    fun cancelEditing() {
        _uiState.value = _uiState.value.copy(
            editingAsset = null,
            editText = ""
        )
    }

    fun updateAssetType(householdId: String, oldAssetType: String, newAssetType: String) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isUpdating = true, error = null)

            repository.updateAssetType(householdId, oldAssetType, newAssetType)
                .onSuccess {
                    _uiState.value = _uiState.value.copy(
                        isUpdating = false,
                        editingAsset = null,
                        editText = ""
                    )
                }
                .onFailure { exception ->
                    _uiState.value = _uiState.value.copy(
                        error = exception.message,
                        isUpdating = false
                    )
                }
        }
    }
}