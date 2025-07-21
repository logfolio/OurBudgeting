package com.fintern.ourbudgeting.ui.statistics.chart

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.fintern.ourbudgeting.ui.statistics.chart.components.MonthSelector
import com.fintern.ourbudgeting.ui.theme.OurBudgetingTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StatisticsScreen(statisticsViewModel: StatisticsViewModel = viewModel()) {
    val uiState by statisticsViewModel.uiState.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    MonthSelector(
                        year = uiState.currentYear,
                        month = uiState.currentMonth,
                        onPreviousMonth = {},
                        onNextMonth = {}
                    )
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier.padding(innerPadding)
        ) {
            // TODO: 통계 화면 본문 작성
        }
    }
}

@Preview
@Composable
fun StatisticsScreenPreview() {
    OurBudgetingTheme {
        StatisticsScreen()
    }
}
