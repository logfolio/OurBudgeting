package com.fintern.ourbudgeting.ui.assetmanagement.assetdisplay

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.fintern.ourbudgeting.R
import com.fintern.ourbudgeting.ui.assetmanagement.assetdisplay.component.AssetDisplayTopAppBar
import com.fintern.ourbudgeting.ui.assetmanagement.assetdisplay.component.FABMenu
import com.fintern.ourbudgeting.ui.assetmanagement.common.component.AssetBody
import com.fintern.ourbudgeting.ui.assetmanagement.common.component.AssetDivider
import com.fintern.ourbudgeting.ui.assetmanagement.common.component.AssetTitle
import com.fintern.ourbudgeting.ui.assetmanagement.common.component.AssetTopSection

@Composable
fun AssetDisplayScreen(
    householdId: String,
    viewModel: AssetDisplayViewModel = hiltViewModel(),
    onAddAssetTypeClick: () -> Unit = {},
    onEditAssetTypeClick: () -> Unit = {}
) {
    val assetSummary by viewModel.assetSummary.collectAsState()

    LaunchedEffect(householdId) {
        viewModel.loadAssetSummary(householdId)
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Scaffold(
            topBar = { AssetDisplayTopAppBar() }
        ) { paddingValue ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.White)
                    .padding(paddingValue)
            ) {
                AssetTopSection(asset = assetSummary.totalAsset, debt = assetSummary.totalDebt)
                AssetDivider()
                val cashResult = viewModel.getAssetDetailByName(stringResource(R.string.cash))
                cashResult?.let {
                    AssetBody(
                        name = stringResource(R.string.cash),
                        amount = it.totalAmount,
                        modifier = Modifier.fillMaxWidth()
                    )
                }

                val bankAssetDetail =
                    viewModel.getAssetDetailContaining(stringResource(R.string.bank))
                if (bankAssetDetail.isNotEmpty()) {
                    LazyColumn(
                        modifier = Modifier.fillMaxWidth(),
                    ) {
                        items(bankAssetDetail) { bankAsset ->
                            AssetBody(
                                name = bankAsset.assetName,
                                amount = bankAsset.totalAmount
                            )
                        }
                    }
                }
            }
        }

        FABMenu(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(end = 16.dp, bottom = 80.dp),
            onAddAssetTypeClick = onAddAssetTypeClick,
            onEditAssetTypeClick = onEditAssetTypeClick
        )
    }
}

@Preview(showBackground = true)
@Composable
fun AssetManagementScreenPreview() {
    MaterialTheme {
        // AssetDisplayScreen()
    }
}