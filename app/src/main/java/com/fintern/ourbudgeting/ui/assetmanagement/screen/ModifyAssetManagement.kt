package com.fintern.ourbudgeting.ui.assetmanagement.screen


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.fintern.ourbudgeting.ui.assetmanagement.component.AssetTitle
import com.fintern.ourbudgeting.ui.assetmanagement.component.AssetTopSection
import com.fintern.ourbudgeting.ui.assetmanagement.component.ModifyAssetBody
import com.fintern.ourbudgeting.ui.assetmanagement.component.ModifyAssetTopAppBar
import com.fintern.ourbudgeting.ui.theme.OurBudgetingTheme


@Composable
fun ModifyAssetManagementScreen(modifier: Modifier = Modifier) {
    Scaffold(
        modifier = modifier.background(Color.White),
        topBar = { ModifyAssetTopAppBar() }
    ) { paddingValue ->
        Column(modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(paddingValue)) {
            AssetTopSection()
            AssetTitle(name = "현금", amount = 1000)
            ModifyAssetBody(name = "현금", amount = 1000)
            AssetTitle(name = "카드", amount = 2000)
            ModifyAssetBody(name = "카드", amount = 1000)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ModifyAssetManagementScreenPreview() {
    OurBudgetingTheme {
        ModifyAssetManagementScreen()
    }
}