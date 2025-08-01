package com.fintern.ourbudgeting.ui.save

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.fintern.ourbudgeting.R
import com.fintern.ourbudgeting.data.model.ExpenseCategoryType
import com.fintern.ourbudgeting.data.model.IncomeCategoryType
import com.fintern.ourbudgeting.ui.common.model.TransactionType
import com.fintern.ourbudgeting.ui.save.componenet.DatePickerField
import com.fintern.ourbudgeting.ui.save.componenet.DropDownField
import com.fintern.ourbudgeting.ui.save.componenet.TransactionToggle
import com.fintern.ourbudgeting.ui.theme.OurBudgetingTheme

@Composable
fun AddTransactionScreen(
    initialTransactionType: TransactionType,
    modifier: Modifier = Modifier,
    viewModel: AddTransactionViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    Scaffold { innerPadding ->
        val categoryOptions = when (uiState.transactionType) {
            TransactionType.EXPENSE -> ExpenseCategoryType.entries
                .map { stringResource(id = it.labelRes) }

            TransactionType.INCOME -> IncomeCategoryType.entries
                .map { stringResource(id = it.labelRes) }
        }

        Column(
            modifier = modifier
                .padding(innerPadding)
                .padding(horizontal = 16.dp)
                .fillMaxWidth()
        ) {
            TransactionToggle(
                transactionType = uiState.transactionType,
                onTransactionTypeChange = {
                    viewModel.setTransactionType(it)
                }
            )

            DatePickerField(
                label = stringResource(R.string.select_date_label),
                onDateSelected = { transactionDate ->
                    viewModel.setSelectedDate(transactionDate)
                },
                modifier = modifier
            )

            // 자산 선택
            DropDownField(
                label = stringResource(R.string.asset),
                options = listOf("은행 계좌", "현금", "카드"),
                // TODO: Firestore에서 데이터 불러와서 표시
                onOptionSelected = { selected ->
                    viewModel.setSelectedAsset(selected)
                },
            )

            // 카테고리 선택
            DropDownField(
                label = stringResource(R.string.category),
                options = categoryOptions,
                onOptionSelected = { selected ->
                    viewModel.setCategory(selected)
                },
            )

            // 금액 입력
            OutlinedTextField(
                value = uiState.amount,
                onValueChange = { newValue ->
                    viewModel.setAmountText(newValue)
                },
                modifier = Modifier.fillMaxWidth(),
                label = { Text(stringResource(R.string.amount)) },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Done
                ),
                singleLine = true
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