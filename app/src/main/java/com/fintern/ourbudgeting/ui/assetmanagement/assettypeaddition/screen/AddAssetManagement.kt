package com.fintern.ourbudgeting.ui.assetmanagement.assettypeaddition.screen

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import com.fintern.ourbudgeting.ui.assetmanagement.AddAssetViewModel
import com.fintern.ourbudgeting.ui.assetmanagement.assettypeaddition.component.AddAssetButton
import com.fintern.ourbudgeting.ui.assetmanagement.assettypeaddition.component.AddAssetOutLinedTextField
import com.fintern.ourbudgeting.ui.assetmanagement.assettypeaddition.component.AddAssetTopAppBar

@Composable
fun AddAssetManagementScreen(
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
        topBar = { AddAssetTopAppBar () }
    ) { paddingValue ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .padding(paddingValue)
        ) {
            AddAssetOutLinedTextField(
                label = "자산유형을 입력해주세요.",
                placeHolder = "은행을 선택해주세요.",
                value = uiState.input,
                onValueChanged ={ newValue->
                    viewModel.updateInput(newValue)
                }
            )
            AddAssetButton(
                title = "추가하기",
                onClick = {
                    viewModel.submitAssetType()
                })
        }
    }
}
