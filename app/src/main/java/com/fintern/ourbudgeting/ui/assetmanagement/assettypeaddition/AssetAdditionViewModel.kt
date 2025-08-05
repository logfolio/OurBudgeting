package com.fintern.ourbudgeting.ui.assetmanagement.assettypeaddition

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fintern.ourbudgeting.data.repository.AssetAdditionRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class AssetAdditionViewModel @Inject constructor(
    private val repository: AssetAdditionRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(AssetTypeUiState())
    val uiState: StateFlow<AssetTypeUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            repository.initializeUserHousehold()
        }
    }

    fun updateInput(newInput: String) {
        _uiState.value = _uiState.value.copy(input = newInput)
    }

    fun clearMessage() {
        _uiState.value = _uiState.value.copy(message = "")
    }

    fun submitAssetType() {
        val text = _uiState.value.input.trim() // 띄어쓰기는 입력으로 취급 안하겠다
        if (text.isBlank()) {
            _uiState.value = _uiState.value.copy(message = "입력값이 비어있습니다")
            return
        }
        _uiState.value = _uiState.value.copy(isLoading = true)
        viewModelScope.launch {
            val result = repository.addAssetType(text)
            if (result.isSuccess) {
                _uiState.value = _uiState.value.copy(
                    input = "",
                    message = "자산유형이 추가되었습니다!",
                    isLoading = false
                )
            } else {
                _uiState.value = _uiState.value.copy(
                    message = "저장 실패: ${result.exceptionOrNull()?.message}",
                    isLoading = false
                )
            }
        }
    }
}
