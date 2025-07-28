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
            AddAssetOutLinedTextField(label = "현금 금액", placeHolder = "현금 금액을 입력해주세요.")
            AddAssetOutLinedTextField(label = "은행", placeHolder = "은행을 선택해주세요.")
            AddAssetOutLinedTextField(label = "금액", placeHolder = "금액을 입력해주세요.")
            AddAssetButton("추가하기")
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