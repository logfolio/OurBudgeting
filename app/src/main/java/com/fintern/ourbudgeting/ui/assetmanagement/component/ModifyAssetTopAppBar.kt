package com.fintern.ourbudgeting.ui.assetmanagement.component

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
import androidx.compose.ui.tooling.preview.Preview
import com.fintern.ourbudgeting.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ModifyAssetTopAppBar(
    modifier: Modifier = Modifier,
    onActionClick: () -> Unit = {}
) {
    TopAppBar(
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color.White,
            titleContentColor = Color.Black,
        ),
        title = { Text("자산 수정") },
        navigationIcon = {
            IconButton(onClick = onActionClick) {
                Icon(
                    painter = painterResource(R.drawable.ic_arrowback),
                    contentDescription = "그래프"
                )
            }
        }
    )
}

@Composable
@Preview
fun ModifyAssetTopAppBarPreview() {
    MaterialTheme {
        ModifyAssetTopAppBar()
    }
}