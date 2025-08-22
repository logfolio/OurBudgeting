package com.fintern.ourbudgeting.ui.calendar

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
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
import com.fintern.ourbudgeting.data.calendar.CategoryList
import com.fintern.ourbudgeting.data.calendar.CategoryType
import com.fintern.ourbudgeting.data.calendar.TransactionWithId
import com.fintern.ourbudgeting.ui.calendar.component.Calendar
import com.fintern.ourbudgeting.ui.calendar.component.CalendarAccountAndUser
import com.fintern.ourbudgeting.ui.calendar.component.CalendarFilterControls
import com.fintern.ourbudgeting.ui.calendar.component.CategoryListSectionItem
import com.fintern.ourbudgeting.ui.calendar.component.FilterType
import com.fintern.ourbudgeting.ui.calendar.component.LabeledAmount
import com.fintern.ourbudgeting.ui.calendar.extensions.toLocalDate
import com.fintern.ourbudgeting.ui.common.model.TransactionType
import com.fintern.ourbudgeting.ui.user.UserViewModel
import java.time.DayOfWeek
import java.time.LocalDate

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CalendarScreen(
    viewModel: TransactionViewModel = hiltViewModel(),
    userViewModel: UserViewModel = hiltViewModel()
) {

    LaunchedEffect(Unit) {
        userViewModel.initializeUserHousehold()
    }

    val household by userViewModel.household.collectAsStateWithLifecycle()
    val nickname by userViewModel.nickname.collectAsStateWithLifecycle()

    val selectedAccount = household?.name ?: ""
    val selectedUser = nickname

    var currentMonth by remember { mutableStateOf(LocalDate.now()) }
    var selectedDate: LocalDate? by remember { mutableStateOf(null) }

    var currentFilterType by remember { mutableStateOf(FilterType.ALL) }

    val uiState = viewModel.transactionsUiState.collectAsStateWithLifecycle()

    val householdId = household?.id
    LaunchedEffect(householdId) {
        if (householdId != null) {
            viewModel.loadTransactions(householdId)
        }
    }

    val transactions: List<TransactionWithId> = when (uiState.value) {
        is TransactionUiState.Loading -> {
            emptyList<TransactionWithId>()
        }

        is TransactionUiState.Success -> {
            val successData = (uiState.value as TransactionUiState.Success).data
            successData
        }

        is TransactionUiState.Error -> {
            emptyList<TransactionWithId>()
        }
    }

    val monthlyTransactionsForTotals = remember(transactions, currentMonth) {
        transactions.filter {
            it.transaction.date?.toLocalDate()?.year == currentMonth.year &&
                    it.transaction.date.toLocalDate().month == currentMonth.month
        }
    }

    val totalIncome = monthlyTransactionsForTotals.filter {
        it.transaction.type == TransactionType.INCOME.name
    }.sumOf { it.transaction.amount }

    val totalExpense = monthlyTransactionsForTotals.filter {
        it.transaction.type == TransactionType.EXPENSE.name
    }.sumOf { it.transaction.amount }

    val categoryListsForUi: List<CategoryList> = remember(transactions, currentMonth) {
        transactions.filter {
            it.transaction.date?.toLocalDate()?.year == currentMonth.year &&
                    it.transaction.date?.toLocalDate()?.month == currentMonth.month
        }
            .groupBy { it.transaction.category }
            .map { (categoryName, transactionList) ->
                val categoryType = CategoryType.entries.find {
                    it.name == categoryName
                } ?: CategoryType.ETC
                CategoryList(
                    category = categoryType,
                    items = transactionList
                )
            }
    }

    val selectedDayTransactions: List<TransactionWithId> =
        remember(transactions, currentMonth, selectedDate, currentFilterType) {
            val filteredByDate = if (selectedDate == null) {
                transactions.filter {
                    it.transaction.date?.toLocalDate()?.year == currentMonth.year &&
                            it.transaction.date?.toLocalDate()?.month == currentMonth.month
                }
            } else {
                transactions.filter {
                    it.transaction.date?.toLocalDate() == selectedDate
                }
            }

            when (currentFilterType) {
                FilterType.INCOME -> filteredByDate.filter { it.transaction.type == TransactionType.INCOME.name }
                FilterType.EXPENSE -> filteredByDate.filter { it.transaction.type == TransactionType.EXPENSE.name }
                FilterType.ALL -> filteredByDate
            }
        }

    val selectedDayCategoryLists: List<CategoryList> = remember(selectedDayTransactions) {
        selectedDayTransactions
            .groupBy { it.transaction.category }
            .map { (categoryName, transactionList) ->
                val categoryType = CategoryType.entries.find {
                    it.name == categoryName
                } ?: CategoryType.ETC
                CategoryList(
                    category = categoryType,
                    items = transactionList
                )
            }
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        item { Spacer(modifier = Modifier.height(16.dp)) }

        item {
            CalendarAccountAndUser(
                selectedAccount = selectedAccount,
                selectedUser = selectedUser,
                onAccountClick = { },
                onUserClick = { }
            )
        }
        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp, horizontal = 16.dp),
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
        }

        item {
            Calendar(
                startDayOfWeek = DayOfWeek.SUNDAY,
                selectedDate = selectedDate,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp),
                categoryLists = categoryListsForUi,
                currentMonth = currentMonth,
                onPreviousClick = {
                    currentMonth = currentMonth.minusMonths(1)
                    selectedDate = null
                },
                onNextClick = {
                    currentMonth = currentMonth.plusMonths(1)
                    selectedDate = null
                },
                onDateClick = { newDate -> selectedDate = newDate }
            )
        }
        item {
            CalendarFilterControls(
                nickname = nickname,
                filterType = currentFilterType,
                onFilterTypeSelected = { newFilterType ->
                    currentFilterType = newFilterType
                },
            )
        }
        items(selectedDayCategoryLists) { categoryList ->
            CategoryListSectionItem(categoryList)
        }
    }
}
