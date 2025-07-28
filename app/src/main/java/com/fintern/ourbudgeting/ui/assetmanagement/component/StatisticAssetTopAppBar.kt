package com.fintern.ourbudgeting.ui.assetmanagement.component

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.fintern.ourbudgeting.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StatisticsAssetTopAppBar(
    modifier: Modifier = Modifier,
    onActionClick: () -> Unit = {}
) {
    var year by rememberSaveable { mutableStateOf(2025) }
    TopAppBar(
        modifier = modifier,
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color.White,
            titleContentColor = Color.Black,
        ),
        actions = {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = { year-- }) {
                    Icon(
                        painter = painterResource(R.drawable.ic_navigate_before),
                        contentDescription = "이전 연도"
                    )
                }
                Text(
                    text = "$year",
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.padding(horizontal = 8.dp)
                )
                IconButton(onClick = { year++ }) {
                    Icon(
                        painter = painterResource(R.drawable.ic_navigate_next),
                        contentDescription = "다음 연도"
                    )
                }
            }
        },
        title = { Text("전체통계") },
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

@Preview(showBackground = true)
@Composable
fun StatisticAssetTopAppBarPreview() {
    MaterialTheme {
        StatisticsAssetTopAppBar()
    }

}
