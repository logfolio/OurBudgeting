package com.fintern.ourbudgeting.ui.assetmanagement.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun StatisticAssetChartTitle(
    modifier: Modifier = Modifier,
    title: String
) {
    Column(modifier = modifier.padding(16.dp)) {
        Text(
            title,
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold
        )
    }
}

@Preview(showBackground = true)
@Composable
fun StatisticAssetChartTitlePreview() {
    MaterialTheme { StatisticAssetChartTitle(title = "누적잔액") }
}

