package com.fintern.ourbudgeting.ui.assetmanagement.common.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Red
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.fintern.ourbudgeting.R

@Composable
fun AssetTopSection(
    modifier: Modifier = Modifier,
    asset: Long,
    debt: Long,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp)
            .background(Color.White),
        horizontalArrangement = Arrangement.Absolute.SpaceAround
    ) {
        Column(
            modifier = Modifier,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(stringResource(R.string.asset))
            Text(stringResource(R.string.amount_won,asset), color = Color.Blue)
        }
        Column(
            modifier = Modifier,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(stringResource(R.string.debt))
            Text(stringResource(R.string.amount_won,debt), color = Red)
        }
        Column(
            modifier = Modifier,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(stringResource(R.string.total))
            Text(stringResource(R.string.amount_won,asset-debt))
        }
    }
}

@Composable
@Preview(showBackground = true)
fun TopSectionPreview() {
    MaterialTheme {
        AssetTopSection(asset = 30000, debt = 50000)
    }
}