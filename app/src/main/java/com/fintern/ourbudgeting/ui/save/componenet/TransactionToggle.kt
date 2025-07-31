package com.fintern.ourbudgeting.ui.save.componenet

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.fintern.ourbudgeting.ui.common.model.TransactionType

@Composable
fun TransactionToggle(
    transactionType: TransactionType,
    onTransactionTypeChange: (TransactionType) -> Unit,
) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        ToggleButton(
            text = TransactionType.INCOME.name,
            isSelected = transactionType == TransactionType.INCOME,
            onClick = { onTransactionTypeChange(TransactionType.INCOME) },
            modifier = Modifier.weight(1f)
        )
        Spacer(modifier = Modifier.width(8.dp))
        ToggleButton(
            text = TransactionType.EXPENSE.name,
            isSelected = transactionType == TransactionType.EXPENSE,
            onClick = { onTransactionTypeChange(TransactionType.EXPENSE) },
            modifier = Modifier.weight(1f)
        )
    }
}

@Composable
fun ToggleButton(
    text: String,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Button(
        onClick = onClick,
        modifier = modifier.height(48.dp),
        shape = RoundedCornerShape(8.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = if (isSelected) Color(0xFF964BFF) else Color.White,
            contentColor = if (isSelected) Color.White else Color(0xFF964BFF)
        ),
        elevation = ButtonDefaults.buttonElevation(0.dp),
        border = BorderStroke(1.dp, Color(0xFF964BFF)),
    ) {
        Text(text)
    }
}