package com.fintern.ourbudgeting.ui.home.components

import android.net.Uri
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.fintern.ourbudgeting.R

@Composable
fun LatestTransactionCard(
    content: String,
    amount: String,
    imageUri: Uri,
    modifier: Modifier = Modifier,
) {
    Card(
        modifier = modifier
            .padding(vertical = 16.dp),
        colors = CardDefaults.cardColors(Color(0xFFE8EDF5)),
        shape = RoundedCornerShape(8.dp)
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
        ) {
            Column {
                Text(
                    text = content,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1,
                    style = MaterialTheme.typography.titleMedium,
                )
                Text(
                    text = stringResource(R.string.amount_won, amount),
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1,
                    style = MaterialTheme.typography.labelMedium,
                )
            }
            AsyncImage(
                model = imageUri,
                contentDescription = stringResource(R.string.image),
            )
        }
    }
}