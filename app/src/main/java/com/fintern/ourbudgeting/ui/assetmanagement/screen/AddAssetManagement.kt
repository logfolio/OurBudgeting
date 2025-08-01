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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.fintern.ourbudgeting.R
import com.fintern.ourbudgeting.ui.assetmanagement.component.AddAssetButton
import com.fintern.ourbudgeting.ui.assetmanagement.component.AddAssetOutLinedTextField
import com.fintern.ourbudgeting.ui.assetmanagement.component.AddAssetTopAppBar

@Composable
fun AddAssetManagementScreen(modifier: Modifier = Modifier) {
    Scaffold(
        modifier = modifier.background(Color.White),
        topBar = { AddAssetTopAppBar() }
    ) { paddingValue ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .padding(paddingValue)
        ) {
            AddAssetOutLinedTextField(
                label = stringResource(R.string.asset_cash_amount), placeHolder = stringResource(
                    R.string.input_cash_amount
                )
            )
            AddAssetOutLinedTextField(
                label = stringResource(R.string.bank), placeHolder = stringResource(
                    R.string.select_bank
                )
            )
            AddAssetOutLinedTextField(
                label = stringResource(R.string.amount), placeHolder = stringResource(
                    R.string.input_amount
                )
            )
            AddAssetButton(stringResource(R.string.add))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AddAssetManagementScreenPreview() {
    MaterialTheme {
        AddAssetManagementScreen()
    }
}