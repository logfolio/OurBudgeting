package com.fintern.ourbudgeting.ui.home.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
import com.fintern.ourbudgeting.ui.theme.OurBudgetingTheme

@Composable
fun AssetSummaryCard(
    amount: String,
    onAddIncomeClick: () -> Unit,
    onAddExpenseClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Card(
        modifier = modifier
            .fillMaxWidth(),
        colors = CardDefaults.cardColors(Color(0xFFE1CBFF)),
        shape = RoundedCornerShape(8.dp)
    ) {
        Column(
            modifier = Modifier.padding(24.dp)
        ) {
            Text(
                stringResource(R.string.total_asset),
                style = MaterialTheme.typography.titleMedium
            )
            Text(
                stringResource(R.string.amount_won, amount),
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
            )

            Spacer(modifier = Modifier.padding(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                TransactionButton(
                    text = stringResource(R.string.add_income),
                    onClick = onAddIncomeClick,
                    containerColor = Color(0xFF964BFF),
                    contentColor = Color.White,
                    modifier = Modifier.weight(1f),
                )
                TransactionButton(
                    text = stringResource(R.string.add_expense),
                    onClick = onAddExpenseClick,
                    containerColor = Color.White,
                    contentColor = Color(0xFF964BFF),
                    modifier = Modifier.weight(1f),
                )
            }
        }
    }
}

@Composable
fun TransactionButton(
    text: String,
    onClick: () -> Unit,
    containerColor: Color,
    contentColor: Color,
    modifier: Modifier = Modifier,
) {
    Button(
        onClick = onClick,
        modifier = modifier,
        shape = RoundedCornerShape(8.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = containerColor,
            contentColor = contentColor,
        ),
    ) {
        Text(text)
    }
}

@Preview(showBackground = true)
@Composable
fun TotalCardPreview() {
    OurBudgetingTheme {
        AssetSummaryCard(amount = "100,000", {}, {})
    }
}