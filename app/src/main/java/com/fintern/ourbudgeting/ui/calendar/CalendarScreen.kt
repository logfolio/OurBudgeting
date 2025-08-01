package com.fintern.ourbudgeting.ui.calendar

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.fintern.ourbudgeting.R
import com.fintern.ourbudgeting.data.calendar.CategoryDefinition
import com.fintern.ourbudgeting.data.calendar.CategoryList
import com.fintern.ourbudgeting.data.calendar.TransactionWithId
import com.fintern.ourbudgeting.ui.calendar.component.Calendar
import com.fintern.ourbudgeting.ui.calendar.component.CalendarAccountAndUser
import com.fintern.ourbudgeting.ui.calendar.component.CalendarFilterControls
import com.fintern.ourbudgeting.ui.calendar.component.CalendarTopAppbar
import com.fintern.ourbudgeting.ui.calendar.component.CategoryListSection
import com.fintern.ourbudgeting.ui.calendar.component.FilterType
import com.fintern.ourbudgeting.ui.calendar.component.LabeledAmount
import com.fintern.ourbudgeting.ui.common.model.TransactionType
import java.time.DayOfWeek
import java.time.LocalDate

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CalendarScreen(
    viewModel: TransactionViewModel = hiltViewModel()
) {

    val householdId = "dlmRP5U0pNhyH7oaIvTy"

    val nickname = "짱구"

    val selectedAccount = remember { mutableStateOf("가계부") }
    val selectedUser = remember { mutableStateOf("조민환") }

    var currentFilterType by remember { mutableStateOf(FilterType.ALL) }

    val uiState = viewModel.transactionsUiState.collectAsStateWithLifecycle()

    LaunchedEffect(householdId, currentFilterType) {
        viewModel.loadTransactions(householdId, currentFilterType)
    }

    val transactions: List<TransactionWithId> = when (uiState.value) {
        is UiState.Loading -> {
            emptyList<TransactionWithId>()
        }
        is UiState.Success -> {
            val successData = (uiState.value as UiState.Success<List<TransactionWithId>>).data
            successData
        }
        is UiState.Error -> {
            emptyList<TransactionWithId>()
        }
    }

    val totalIncome = transactions.filter {
        it.transaction.type == TransactionType.INCOME
    }.sumOf { it.transaction.amount }

    val totalExpense = transactions.filter {
        it.transaction.type == TransactionType.EXPENSE
    }.sumOf { it.transaction.amount }

    val categoryListsForUi: List<CategoryList> = remember(transactions) {
        transactions
            .groupBy { it.transaction.category }
            .map { (categoryName, transactionList) ->
                CategoryList(
                    category = CategoryDefinition(
                        id = categoryName,
                        emoji = "🍔",
                        displayName = "식비"
                    ),
                    items = transactionList
                )
            }
    }

    Scaffold(
        topBar = {
            CalendarTopAppbar(
                title = stringResource(R.string.calendar_label_app_name),
                modifier = Modifier
                    .fillMaxWidth()
            )
        },
        content = { innerPadding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .background(Color.White)
            ) {
                CalendarAccountAndUser(
                    selectedAccount = selectedAccount.value,
                    selectedUser = selectedUser.value,
                    onAccountClick = { },
                    onUserClick = { }
                )
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 8.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    LabeledAmount(
                        label = stringResource(R.string.label_income),
                        labelColor = Color.Red,
                        amount = totalIncome,
                        amountBoxWidth = 120.dp
                    )

                    Spacer(modifier = Modifier.width(28.dp))

                    LabeledAmount(
                        label = stringResource(R.string.label_expense),
                        labelColor = Color.Blue,
                        amount = totalExpense,
                        amountBoxWidth = 120.dp
                    )
                }

                Calendar(
                    startDayOfWeek = DayOfWeek.SUNDAY,
                    selectedDate = LocalDate.now(),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 24.dp),
                    categoryLists = categoryListsForUi,
                )
                CalendarFilterControls(
                    nickname = nickname,
                    filterType = currentFilterType,
                    onFilterTypeSelected = {newFilterType ->
                        currentFilterType = newFilterType
                    },
                )

                CategoryListSection(
                    categories = categoryListsForUi
                )
            }
        }
    )
}
