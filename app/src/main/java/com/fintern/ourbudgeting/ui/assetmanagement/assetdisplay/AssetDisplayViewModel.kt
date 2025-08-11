package com.fintern.ourbudgeting.ui.assetmanagement.assetdisplay

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fintern.ourbudgeting.data.repository.AssetDisplayRepository
import com.fintern.ourbudgeting.ui.assetmanagement.data.AssetDetail
import com.fintern.ourbudgeting.ui.assetmanagement.data.AssetSummary
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class AssetDisplayViewModel @Inject constructor(
    private val repository: AssetDisplayRepository
): ViewModel() {

    private val _assetSummary = MutableStateFlow(AssetSummary())
    val assetSummary: StateFlow<AssetSummary> = _assetSummary.asStateFlow()

    private val _currentHouseholdId = MutableStateFlow("")
    val currentHouseholdId: StateFlow<String> = _currentHouseholdId.asStateFlow()

    fun loadAssetSummary(householdId: String) {
        _currentHouseholdId.value = householdId
        repository.getAssetSummary(householdId)
            .onEach { result ->
                result.onSuccess { summary ->
                    _assetSummary.value = summary
                }.onFailure { throwable ->
                    _assetSummary.value = AssetSummary()
                }
            }
            .launchIn(viewModelScope)
    }
    fun getAssetDetailByName(assetName: String): AssetDetail? {
        return _assetSummary.value.assetDetails.find { it.assetName == assetName }
    }
    fun getAssetDetailContaining(keyword: String): List<AssetDetail> {
        return _assetSummary.value.assetDetails.filter { it.assetName.contains(keyword) }
    }
    fun getAssetDetailContainingTotalAmount(keyword: String): Long {
        return _assetSummary.value.assetDetails.filter { it.assetName.contains(keyword) }.sumOf { it.totalAmount }
    }
}