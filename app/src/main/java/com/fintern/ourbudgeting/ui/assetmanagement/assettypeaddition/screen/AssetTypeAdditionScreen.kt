package com.fintern.ourbudgeting.ui.assetmanagement.assettypeaddition.screen

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.fintern.ourbudgeting.ui.assetmanagement.assettypeaddition.AddAssetViewModel
import com.fintern.ourbudgeting.ui.assetmanagement.assettypeaddition.component.AssetTypeAdditionTopAppBar
import com.fintern.ourbudgeting.ui.assetmanagement.assettypeaddition.component.AssetTypeAdditionBody
import com.fintern.ourbudgeting.ui.assetmanagement.assettypeaddition.component.AssetTypeAdditionButton

@Composable
fun AssetTypeAdditionScreen(
    viewModel: AddAssetViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val context = LocalContext.current

    LaunchedEffect(uiState.message) {
        if (uiState.message.isNotEmpty()) {
            Toast.makeText(context, uiState.message, Toast.LENGTH_SHORT).show()
            viewModel.clearMessage()
        }
    }
    Scaffold(
        modifier = Modifier.background(Color.White),
        topBar = { AssetTypeAdditionTopAppBar() }
    ) { paddingValue ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .padding(paddingValue)
        ) {
            AssetTypeAdditionBody(
                label = "자산유형을 입력해주세요.",
                placeHolder = "은행을 선택해주세요.",
                value = "" ,//""
                onValueChanged ={ newValue->
                    viewModel.updateInput(newValue)
                }
            )
            AssetTypeAdditionButton(
                title = "추가하기",
                onClick = {
                    viewModel.submitAssetType()
                })

        }
    }
}

@Preview(showBackground = true)
@Composable
fun AssetTypeAdditionScreenPreview() {
    MaterialTheme {
        AssetTypeAdditionScreen()
    }
}