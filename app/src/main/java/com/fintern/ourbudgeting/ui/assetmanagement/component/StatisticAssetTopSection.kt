package com.fintern.ourbudgeting.ui.assetmanagement.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.fintern.ourbudgeting.R

@Composable
fun StatisticAssetTopSection(
    modifier: Modifier = Modifier,
    totalPrice: Long
) {
    Column(modifier = modifier.padding(16.dp)) {
        Text(
            stringResource(R.string.cumulative_balance),
            style = MaterialTheme.typography.titleMedium
        )
        Text(
            text = "${totalPrice}원",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold
        )
    }
}

@Preview(showBackground = true)
@Composable
fun StatisticAssetTopSectionPreview() {
    MaterialTheme { StatisticAssetTopSection(totalPrice = 300000) }
}