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
import androidx.compose.ui.res.stringResource
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
                        contentDescription = stringResource(R.string.previous_year)
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
                        contentDescription = stringResource(R.string.next_year)
                    )
                }
            }
        },
        title = { Text(stringResource(R.string.total_statistic)) },
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

@Preview(showBackground = true)
@Composable
fun StatisticAssetTopAppBarPreview() {
    MaterialTheme {
        StatisticsAssetTopAppBar()
    }
}
