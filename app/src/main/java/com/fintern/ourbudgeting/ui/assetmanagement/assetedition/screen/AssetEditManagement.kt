package com.fintern.ourbudgeting.ui.assetmanagement.assetedition.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.fintern.ourbudgeting.R
import com.fintern.ourbudgeting.ui.assetmanagement.assetedition.component.AssetEditBody
import com.fintern.ourbudgeting.ui.assetmanagement.assetedition.component.AssetEditTopAppBar
import com.fintern.ourbudgeting.ui.assetmanagement.common.component.AssetTitle
import com.fintern.ourbudgeting.ui.assetmanagement.common.component.AssetTopSection
import com.fintern.ourbudgeting.ui.theme.OurBudgetingTheme

@Composable
fun AssetEditManagement(
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
            AssetTitle(name = stringResource(R.string.cash), amount = 1000)
            AssetEditBody(name = stringResource(R.string.cash), amount = 1000)
            AssetTitle(name = stringResource(R.string.card), amount = 2000)
            AssetEditBody(name = stringResource(R.string.card), amount = 1000)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ModifyAssetManagementScreenPreview() {
    OurBudgetingTheme {
        AssetEditManagement(asset = 300, debt = 2000)
    }
}