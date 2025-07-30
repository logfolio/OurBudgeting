package com.fintern.ourbudgeting.ui.assetmanagement.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.fintern.ourbudgeting.ui.assetmanagement.component.AssetBody
import com.fintern.ourbudgeting.ui.assetmanagement.component.AssetHorizontalDivider
import com.fintern.ourbudgeting.ui.assetmanagement.component.AssetTitle
import com.fintern.ourbudgeting.ui.assetmanagement.component.AssetTopAppBar
import com.fintern.ourbudgeting.ui.assetmanagement.component.AssetTopSection

@Composable
fun AssetManagementScreen(
    modifier: Modifier = Modifier,
    asset: Long,
    dept:Long,
) {
    Scaffold(
        modifier = modifier.background(Color.White),
        topBar = { AssetTopAppBar() },
        floatingActionButton = { }
    ) { paddingValue ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .padding(paddingValue)
        ) {
            AssetTopSection(asset = asset, debt = dept)
            AssetHorizontalDivider()
            AssetTitle(name = "현금", amount = 1000)
            AssetBody(name = "현금", amount = 1000)
            AssetTitle(name = "카드", amount = 2000)
            AssetBody(name = "카드", amount = 1000)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AssetManagementScreenPreview() {
    MaterialTheme {
        AssetManagementScreen(asset = 1000, dept = 2000)
    }
}