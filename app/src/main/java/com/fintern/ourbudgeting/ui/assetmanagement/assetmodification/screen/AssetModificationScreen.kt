package com.fintern.ourbudgeting.ui.assetmanagement.assetmodification.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.fintern.ourbudgeting.ui.assetmanagement.assetmodification.component.AssetModificationBody
import com.fintern.ourbudgeting.ui.assetmanagement.assetmodification.component.AssetModificationTopAppBr
import com.fintern.ourbudgeting.ui.assetmanagement.common.component.AssetDisplayTitle
import com.fintern.ourbudgeting.ui.assetmanagement.common.component.AssetDisplayTopSection
import com.fintern.ourbudgeting.ui.theme.OurBudgetingTheme

@Composable
fun AssetModificationScreen(
    modifier: Modifier = Modifier,
    asset: Long,
    debt: Long
) {
    Scaffold(
        modifier = modifier.background(Color.White),
        topBar = { AssetModificationTopAppBr() }
    ) { paddingValue ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .padding(paddingValue)
        ) {
            AssetDisplayTopSection(asset = asset, debt = debt)
            AssetDisplayTitle(name = "현금", amount = 1000)
            AssetModificationBody(name = "현금", amount = 1000)
            AssetDisplayTitle(name = "카드", amount = 2000)
            AssetModificationBody(name = "카드", amount = 1000)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AssetModificationScreenPreview() {
    OurBudgetingTheme {
        AssetModificationScreen(asset = 300, debt = 2000)
    }
}