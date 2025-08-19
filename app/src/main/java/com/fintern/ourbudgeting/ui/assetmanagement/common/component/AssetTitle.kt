package com.fintern.ourbudgeting.ui.assetmanagement.common.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.fintern.ourbudgeting.R

@Composable
fun AssetTitle(
    modifier: Modifier = Modifier,
    name: String,
    amount: Long
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = name,
            color = Color.Black,
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold
        )
        Text(
            color = Color.Black,
            text = stringResource(R.string.amount_won, amount),
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
@Preview(showBackground = true)
fun CashTitlePreview() {
    MaterialTheme {
        AssetTitle(name = "현금", amount = 0)
    }
}

@Composable
@Preview(showBackground = true)
fun BankTitlePreview() {
    MaterialTheme {
        AssetTitle(name = "은행", amount = 0)
    }
}