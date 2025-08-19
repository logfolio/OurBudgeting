package com.fintern.ourbudgeting.ui.assetmanagement.assetedition.component

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.fintern.ourbudgeting.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AssetEditTopAppBar(
    modifier: Modifier = Modifier,
    onActionClick: () -> Unit = {}
) {
    TopAppBar(
        colors = TopAppBarDefaults.topAppBarColors(
            navigationIconContentColor = Color.Black,
            containerColor = Color.White,
            titleContentColor = Color.Black,
        ),
        title = { Text(stringResource(R.string.edit_asset_type)) },
        navigationIcon = {
            IconButton(onClick = onActionClick) {
                Icon(
                    painter = painterResource(R.drawable.ic_arrowback),
                    contentDescription = stringResource(R.string.graph)
                )
            }
        }
    )
}

@Composable
@Preview
fun ModifyAssetTopAppBarPreview() {
    MaterialTheme {
        AssetEditTopAppBar()
    }
}