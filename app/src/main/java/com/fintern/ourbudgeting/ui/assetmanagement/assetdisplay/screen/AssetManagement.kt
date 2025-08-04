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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.fintern.ourbudgeting.R
import com.fintern.ourbudgeting.ui.assetmanagement.common.component.AssetBody
import com.fintern.ourbudgeting.ui.assetmanagement.common.component.AssetHorizontalDivider
import com.fintern.ourbudgeting.ui.assetmanagement.common.component.AssetTitle
import com.fintern.ourbudgeting.ui.assetmanagement.assetdisplay.component.AssetTopAppBar
import com.fintern.ourbudgeting.ui.assetmanagement.common.component.AssetTopSection

@Composable
fun AssetManagementScreen(
    modifier: Modifier = Modifier,
    asset: Long,
    dept: Long,
) {
    Scaffold(
        modifier = modifier,
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
            AssetTitle(name = stringResource(R.string.cash), amount = 1000)
            AssetBody(name = stringResource(R.string.cash), amount = 1000)
            AssetTitle(name = stringResource(R.string.card), amount = 2000)
            AssetBody(name = stringResource(R.string.card), amount = 1000)
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