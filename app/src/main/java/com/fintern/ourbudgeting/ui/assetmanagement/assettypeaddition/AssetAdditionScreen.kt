package com.fintern.ourbudgeting.ui.assetmanagement.assettypeaddition

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
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.fintern.ourbudgeting.R
import com.fintern.ourbudgeting.ui.assetmanagement.assettypeaddition.component.AssetAdditionBody
import com.fintern.ourbudgeting.ui.assetmanagement.assettypeaddition.component.AssetAdditionButton
import com.fintern.ourbudgeting.ui.assetmanagement.assettypeaddition.component.AssetAdditionTopAppBar

@Composable
fun AssetAdditionScreen(
    viewModel: AssetAdditionViewModel = hiltViewModel(),
    householdId:String
) {
    val uiState by viewModel.uiState.collectAsState()
    val context = LocalContext.current

    LaunchedEffect(uiState.message) {
        if (uiState.message != null) {
            Toast.makeText(
                context,
                context.getString(uiState.message!!.messageResId),
                Toast.LENGTH_SHORT
            ).show()
            viewModel.clearMessage()
        }
    }
    val defaultAssetType = stringResource(id = R.string.cash)
    LaunchedEffect(Unit) {
        viewModel.initializeUserHousehold(defaultAssetType,householdId)
    }
    Scaffold(
        modifier = Modifier.background(Color.White),
        topBar = { AssetAdditionTopAppBar() }
    ) { paddingValue ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .padding(paddingValue)
        ) {
            AssetAdditionBody(
                label = stringResource(R.string.type_asset_type),
                placeHolder = stringResource(R.string.type_asset_type),
                value = uiState.input,
                onValueChanged = { newValue ->
                    viewModel.updateInput(newValue)
                }
            )
            AssetAdditionButton(
                title = stringResource(R.string.add),
                onClick = {
                    viewModel.submitAssetType(householdId)
                })
        }
    }
}
