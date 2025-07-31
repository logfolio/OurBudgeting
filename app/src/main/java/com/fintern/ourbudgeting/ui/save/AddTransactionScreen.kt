package com.fintern.ourbudgeting.ui.save

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.fintern.ourbudgeting.R
import com.fintern.ourbudgeting.ui.common.model.TransactionType
import com.fintern.ourbudgeting.ui.save.componenet.DatePickerField
import com.fintern.ourbudgeting.ui.save.componenet.TransactionToggle
import com.fintern.ourbudgeting.ui.theme.OurBudgetingTheme

@Composable
fun AddTransactionScreen(
    initialTransactionType: TransactionType,
    modifier: Modifier = Modifier,
) {
    var uiState by remember {
        mutableStateOf(AddTransactionUiState(transactionType = initialTransactionType))
    }

    Scaffold { innerPadding ->
        Column(
            modifier = modifier
                .padding(innerPadding)
                .padding(horizontal = 16.dp)
                .fillMaxWidth()
        ) {
            TransactionToggle(
                transactionType = uiState.transactionType,
                onTransactionTypeChange = {
                    uiState = uiState.copy(transactionType = it)
                }
            )

            DatePickerField(
                label = stringResource(R.string.select_date_label),
                onDateSelected = { transactionDate ->
                    uiState = uiState.copy(selectedDate = transactionDate)
                },
                modifier = modifier
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