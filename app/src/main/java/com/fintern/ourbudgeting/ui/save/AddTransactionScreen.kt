package com.fintern.ourbudgeting.ui.save

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.fintern.ourbudgeting.ui.common.model.TransactionType
import com.fintern.ourbudgeting.ui.save.componenet.TransactionToggle
import com.fintern.ourbudgeting.ui.theme.OurBudgetingTheme

@Composable
fun AddTransactionScreen(
    initialTransactionType: TransactionType,
    modifier: Modifier = Modifier,
) {
    var transactionType by remember { mutableStateOf(initialTransactionType) }

    Scaffold { innerPadding ->
        Column(
            modifier = modifier.padding(innerPadding)
        ) {
            TransactionToggle(
                transactionType = transactionType,
                onTransactionTypeChange = { transactionType = it },
            )
        }
    }
}

@Preview
@Composable
fun Preview() {
    OurBudgetingTheme {
        AddTransactionScreen(TransactionType.EXPENSE)
    }
}