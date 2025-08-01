package com.fintern.ourbudgeting.ui.assetmanagement.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun AssetHorizontalDivider(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .padding(16.dp)
            .fillMaxWidth()
            .background(Color.White)
    ) {
        HorizontalDivider(thickness = 2.dp)
    }
}

@Preview(showBackground = true)
@Composable
fun HorizontalDividerPreview() {
    MaterialTheme {
        AssetHorizontalDivider()
    }
}
