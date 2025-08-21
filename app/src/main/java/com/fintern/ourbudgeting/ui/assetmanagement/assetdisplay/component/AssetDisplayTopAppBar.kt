package com.fintern.ourbudgeting.ui.assetmanagement.assetdisplay.component

import androidx.compose.foundation.layout.WindowInsets
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
fun AssetDisplayTopAppBar(
    modifier: Modifier = Modifier,
    onActionClick: () -> Unit = {}
) {
    TopAppBar(
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color.White,
            titleContentColor = Color.Black,
        ),
        title = { Text(stringResource(R.string.asset_management)) },
        actions = {
            IconButton(onClick = onActionClick) {
                Icon(
                    tint = Color.Black,
                    painter = painterResource(R.drawable.ic_graph),
                    contentDescription = stringResource(R.string.graph)
                )
            }
        },
        windowInsets = WindowInsets(0)
    )
}

@Composable
@Preview
fun AssetManagementTopAppBarPreview() {
    MaterialTheme {
        AssetDisplayTopAppBar()
    }
}