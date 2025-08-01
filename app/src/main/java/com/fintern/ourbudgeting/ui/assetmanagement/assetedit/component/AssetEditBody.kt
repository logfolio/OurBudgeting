package com.fintern.ourbudgeting.ui.assetmanagement.assetedit.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.fintern.ourbudgeting.R

@Composable
fun AssetEditBody(
    modifier: Modifier = Modifier,
    name: String,
    amount: Long,
    onModifyClick: () -> Unit = {},
    onDeleteClick: () -> Unit = {}
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = name,
            style = MaterialTheme.typography.titleMedium,
        )
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            IconButton(
                onClick = onModifyClick
            ) {
                Icon(
                    painter = painterResource(R.drawable.ic_edit),                     contentDescription = stringResource(R.string.modify)
                )
            }
            IconButton(
                onClick = onDeleteClick
            ) {
                Icon(
                    painter = painterResource(R.drawable.ic_delete), 
                    contentDescription = stringResource(R.string.delete)
                )
            }
            Text(
                text = "${amount}원",
                style = MaterialTheme.typography.titleMedium,
            )
        }
    }
}

@Composable
@Preview(showBackground = true)
fun CashAssetBodyPreview() {
    MaterialTheme {
        AssetEditBody(name = "현금", amount = 12200000)
    }
}

@Composable
@Preview(showBackground = true)
fun BankAssetBodyPreview() {
    MaterialTheme {
        AssetEditBody(name = "은행", amount = 1000000)
    }
}