package com.fintern.ourbudgeting.ui.calendar.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun LabeledAmount(
    label: String,
    labelColor: Color,
    amount: Long,
    modifier: Modifier = Modifier,
    amountBoxWidth: Dp = 120.dp
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = label,
            color = labelColor,
            fontSize = 16.sp
        )
        Spacer(modifier = Modifier.width(28.dp))
        Box(
            modifier = Modifier.width(amountBoxWidth)
        ) {
            Text(
                text = formatCurrency(amount),
                fontSize = 14.sp,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.End
            )
        }
    }
}

fun formatCurrency(amount: Long): String {
    return "%,d원".format(amount)
}