package com.fintern.ourbudgeting.ui.assetmanagement.assetedit.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.fintern.ourbudgeting.ui.assetmanagement.common.component.AssetTitle
import com.fintern.ourbudgeting.ui.assetmanagement.common.component.AssetTopSection
import com.fintern.ourbudgeting.ui.assetmanagement.assetedit.component.AssetEditBody
import com.fintern.ourbudgeting.ui.assetmanagement.assetedit.component.AssetEditTopAppBar
import com.fintern.ourbudgeting.ui.theme.OurBudgetingTheme

@Composable
fun ModifyAssetManagementScreen(
    modifier: Modifier = Modifier,
    asset: Long,
    debt: Long
) {
    Scaffold(
        modifier = modifier.background(Color.White),
        topBar = { AssetEditTopAppBar() }
    ) { paddingValue ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .padding(paddingValue)
        ) {
            AssetTopSection(asset = asset, debt = debt)
            AssetTitle(name = "현금", amount = 1000)
            AssetEditBody(name = "현금", amount = 1000)
            AssetTitle(name = "카드", amount = 2000)
            AssetEditBody(name = "카드", amount = 1000)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ModifyAssetManagementScreenPreview() {
    OurBudgetingTheme {
        ModifyAssetManagementScreen(asset = 300, debt = 2000)
    }
}