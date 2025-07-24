package com.fintern.ourbudgeting.ui.statistics.chart

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.fintern.ourbudgeting.R
import com.fintern.ourbudgeting.ui.statistics.chart.components.MonthSelector
import com.fintern.ourbudgeting.ui.theme.OurBudgetingTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StatisticsScreen(
    uid: String,
    householdId: String,
    statisticsViewModel: StatisticsViewModel = hiltViewModel()
) {
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
                }, actions = {
                    IconButton(
                        onClick = {
                            // TODO: AI 패턴 분석 화면 이동
                        }
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.ic_ai_analysis),
                            contentDescription = stringResource(R.string.ai_analysis)
                        )
                    }
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
        StatisticsScreen(
            uid = "",
            householdId = ""
        )
    }
}