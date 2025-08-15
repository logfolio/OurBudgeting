package com.fintern.ourbudgeting.ui.assetmanagement.assetedition

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.fintern.ourbudgeting.ui.assetmanagement.assetedition.component.AssetEditBody
import com.fintern.ourbudgeting.ui.assetmanagement.assetedition.component.AssetEditTopAppBar
import com.fintern.ourbudgeting.ui.assetmanagement.common.component.AssetTypeListTitle
import com.fintern.ourbudgeting.ui.theme.OurBudgetingTheme

@Composable
fun AssetEditScreen(
    modifier: Modifier = Modifier,
    onNavigateBack: () -> Unit = {},
    viewModel: AssetEditViewModel = hiltViewModel(),
    householdId: String,
) {

    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(householdId) {
        viewModel.observeAndGetAssetTypes(householdId)
    }
    Scaffold(
        modifier = modifier.background(Color.White),
        topBar = { AssetEditTopAppBar(onActionClick = onNavigateBack) }
    ) { paddingValue ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .padding(paddingValue)
        ) {
            AssetTypeListTitle()
            if (uiState.isLoading) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxWidth(),
                ) {
                    items(
                        items = uiState.assetTypes,
                        key = { it }
                    ) { assetType ->
                        AssetEditBody(
                            name = assetType,
                            editText = if (uiState.editingAsset == assetType) uiState.editText else "",
                            isEditing = uiState.editingAsset == assetType,
                            isUpdating = uiState.isUpdating && uiState.editingAsset == assetType,
                            onEditingChange = { isEditing ->
                                if (isEditing) {
                                    viewModel.startEditing(assetType)
                                } else {
                                    viewModel.cancelEditing()
                                }
                            },
                            onEditTextChange = { text ->
                                viewModel.updateEditText(text)
                            },
                            onEditComplete = { oldName, newName ->
                                viewModel.updateAssetType(householdId, oldName, newName)
                            }
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ModifyAssetManagementScreenPreview() {
    OurBudgetingTheme {
        AssetEditScreen(householdId = "")
    }
}