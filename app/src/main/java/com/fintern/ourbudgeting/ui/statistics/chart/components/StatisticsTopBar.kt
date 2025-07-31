package com.fintern.ourbudgeting.ui.statistics.chart.components

import androidx.compose.foundation.layout.Row
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import com.fintern.ourbudgeting.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StatisticsTopBar(
    year: Int,
    month: Int,
    onPreviousMonth: () -> Unit,
    onNextMonth: () -> Unit,
    onAiAnalysisClick: () -> Unit
) {
    TopAppBar(
        title = {
            MonthSelector(
                year = year,
                month = month,
                onPreviousMonth = onPreviousMonth,
                onNextMonth = onNextMonth
            )
        },
        actions = {
            IconButton(onClick = onAiAnalysisClick) {
                Icon(
                    painter = painterResource(R.drawable.ic_ai_analysis),
                    contentDescription = stringResource(R.string.ai_analysis)
                )
            }
        }
    )
}

@Composable
fun MonthSelector(
    year: Int,
    month: Int,
    onPreviousMonth: () -> Unit,
    onNextMonth: () -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
    ) {
        IconButton(
            onClick = { onPreviousMonth() }
        ) {
            Icon(
                painter = painterResource(R.drawable.ic_chevron_left),
                contentDescription = stringResource(R.string.previous_month)
            )
        }

        Text(
            text = "${year}년 ${month}월",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold
        )

        IconButton(
            onClick = { onNextMonth() }
        ) {
            Icon(
                painter = painterResource(R.drawable.ic_chevron_right),
                contentDescription = stringResource(R.string.next_month)
            )
        }
    }
}