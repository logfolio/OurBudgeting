package com.fintern.ourbudgeting.ui.calendar.component

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.fintern.ourbudgeting.R

@Composable
fun CalendarTransactionFilter(
    nickname: String,
    filterType: FilterType,
    modifier: Modifier = Modifier,
    onFilterClick: () -> Unit
) {

    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(start = 16.dp, end = 16.dp, top = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            buildAnnotatedString {
                withStyle(style = SpanStyle(fontSize = 16.sp, fontWeight = FontWeight.Bold)) {
                    append(nickname)
                }
                withStyle(style = SpanStyle(fontSize = 14.sp, fontWeight = FontWeight.Normal)) {
                    append(stringResource(R.string.label_about_name))
                }
                withStyle(style = SpanStyle(fontSize = 16.sp, fontWeight = FontWeight.Bold)) {
                    append(stringResource(filterType.label))
                }
                withStyle(style = SpanStyle(fontSize = 16.sp, fontWeight = FontWeight.Bold)) {
                    append(stringResource(R.string.label_transaction_history))
                }
            }
        )

        Spacer(modifier = Modifier.weight(1f))

        Column(
            horizontalAlignment = Alignment.End
        ) {
            CustomFilterButton(
                onClick = onFilterClick,
                contentDescription = "Filter",
            )
        }
    }
}

enum class FilterType(@StringRes val label: Int) {
    ALL(R.string.label_filter_all),
    INCOME(R.string.label_filter_income),
    EXPENSE(R.string.label_filter_expense)
}
