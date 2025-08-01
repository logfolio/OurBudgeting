package com.fintern.ourbudgeting.ui.assetmanagement.assetdisplay.screen

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
import com.fintern.ourbudgeting.ui.assetmanagement.assetdisplay.component.AssetDisplayTopAppBar
import com.fintern.ourbudgeting.ui.assetmanagement.common.component.AssetDisplayBody
import com.fintern.ourbudgeting.ui.assetmanagement.common.component.AssetDisplayDevider
import com.fintern.ourbudgeting.ui.assetmanagement.common.component.AssetDisplayTitle
import com.fintern.ourbudgeting.ui.assetmanagement.common.component.AssetDisplayTopSection

@Composable
fun AssetDisplayScreen(
    modifier: Modifier = Modifier,
    asset: Long,
    dept:Long,
) {
    Scaffold(
//        modifier = modifier.background(Color.White),
        topBar = { AssetDisplayTopAppBar() },
        floatingActionButton = { },
//        containerColor = Color.White
    ) { paddingValue ->
        Column(
            modifier = Modifier
               .background(Color.White)
                .fillMaxSize()
                .padding(paddingValue)
        ) {
            AssetDisplayTopSection(asset = asset, debt = dept)
            AssetDisplayDevider()
            AssetDisplayTitle(name = "현금", amount = 1000)
            AssetDisplayBody(name = "현금", amount = 1000)
            AssetDisplayTitle(name = "카드", amount = 2000)
            AssetDisplayBody(name = "카드", amount = 1000)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AssetDisplayScreenPreview() {
    MaterialTheme {
        AssetDisplayScreen(asset = 1000, dept = 2000)
    }
}