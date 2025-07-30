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
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.fintern.ourbudgeting.R
import com.fintern.ourbudgeting.data.calendar.CategoryDefinition
import com.fintern.ourbudgeting.data.calendar.CategoryItemData
import com.fintern.ourbudgeting.data.calendar.CategoryList
import com.fintern.ourbudgeting.ui.calendar.component.Calendar
import com.fintern.ourbudgeting.ui.calendar.component.CalendarAccountAndUser
import com.fintern.ourbudgeting.ui.calendar.component.CalendarTopAppbar
import com.fintern.ourbudgeting.ui.calendar.component.CalendarTransactionFilter
import com.fintern.ourbudgeting.ui.calendar.component.CategoryListSection
import com.fintern.ourbudgeting.ui.calendar.component.FilterType
import com.fintern.ourbudgeting.ui.calendar.component.LabeledAmount
import com.fintern.ourbudgeting.ui.calendar.component.toTimestamp
import com.fintern.ourbudgeting.ui.common.model.TransactionType
import com.google.firebase.Timestamp
import java.time.DayOfWeek
import java.time.LocalDate

@Preview
@Composable
fun CalendarScreen() {

    val sampleIncomeAmount = 100_000_000L
    val sampleExpenseAmount = 50_000_000L

    val nickname = "짱구"
    var filterType by remember { mutableStateOf(FilterType.ALL) }

    val selectedAccount = remember { mutableStateOf("가계부") }
    val selectedUser = remember { mutableStateOf("조민환") }

    val sampleCategoryLists = listOf(
        CategoryList(
            category = CategoryDefinition("food", "🍔", "식비"),
            items = listOf(
                CategoryItemData(
                    id = "1",
                    amount = 2000L,
                    description = "햄버거",
                    date = Timestamp.now(),
                    userName = "짱구",
                    type = TransactionType.EXPENSE,
                    categoryId = "food"
                ),
                CategoryItemData(
                    id = "2",
                    amount = 3000L,
                    description = "햄버거",
                    date = Timestamp.now(),
                    userName = "짱구",
                    type = TransactionType.INCOME,
                    categoryId = "food"
                ),
                CategoryItemData(
                    id = "3",
                    amount = 3000L,
                    description = "햄버거",
                    date = toTimestamp("2025/07/21"),
                    userName = "짱구",
                    type = TransactionType.EXPENSE,
                    categoryId = "food"
                )
            )
        )
    )

    Scaffold(
        topBar = {
            CalendarTopAppbar(
                title = stringResource(R.string.calendar_label_app_name)
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
                        amount = sampleIncomeAmount,
                        amountBoxWidth = 120.dp
                    )

                    Spacer(modifier = Modifier.width(28.dp))

                    LabeledAmount(
                        label = stringResource(R.string.label_expense),
                        labelColor = Color.Blue,
                        amount = sampleExpenseAmount,
                        amountBoxWidth = 120.dp
                    )
                }

                Calendar(
                    startDayOfWeek = DayOfWeek.SUNDAY,
                    selectedDate = LocalDate.now(),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 24.dp),
                    categoryLists = sampleCategoryLists,
                )
                CalendarTransactionFilter(
                    nickname = nickname,
                    filterType = filterType,
                )
                CategoryListSection(
                    categories = sampleCategoryLists
                )
            }
        }
    )
}
