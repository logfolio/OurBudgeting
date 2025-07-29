package com.fintern.ourbudgeting.ui.statistics.chart

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.PrimaryTabRow
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.fintern.ourbudgeting.R
import com.fintern.ourbudgeting.ui.statistics.chart.components.StatisticsContent
import com.fintern.ourbudgeting.ui.statistics.chart.components.StatisticsTab
import com.fintern.ourbudgeting.ui.statistics.chart.components.StatisticsTopBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StatisticsScreen(
    uid: String,
    householdId: String,
    statisticsViewModel: StatisticsViewModel = hiltViewModel()
) {
    val uiState by statisticsViewModel.uiState.collectAsState()
    val startDestination = StatisticsTab.EXPENSE
    var selectedTabIndex by rememberSaveable { mutableIntStateOf(startDestination.ordinal) }

    LaunchedEffect(Unit) {
        statisticsViewModel.initialize(uid, householdId)
    }

    Scaffold(
        topBar = {
            StatisticsTopBar(
                year = uiState.currentYear,
                month = uiState.currentMonth,
                onPreviousMonth = {
                    statisticsViewModel.updateMonthAndFetchData(
                        offset = -1,
                        uid = uid,
                        householdId = householdId
                    )
                },
                onNextMonth = {
                    statisticsViewModel.updateMonthAndFetchData(
                        offset = 1,
                        uid = uid,
                        householdId = householdId
                    )
                },
                onAiAnalysisClick = {
                    // TODO: AI 패턴 분석 화면 이동
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
        ) {
            PrimaryTabRow(
                selectedTabIndex = selectedTabIndex,
            ) {
                StatisticsTab.entries.forEachIndexed { index, destination ->
                    Tab(
                        selected = selectedTabIndex == index,
                        onClick = {
                            selectedTabIndex = index
                            statisticsViewModel.updateTypeAndFetchData(
                                type = destination.type,
                                uid = uid,
                                householdId = householdId
                            )
                        },
                        text = {
                            Text(
                                text = destination.label,
                                overflow = TextOverflow.Ellipsis
                            )
                        }
                    )
                }
            }
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp)
            ) {
                when {
                    uiState.isLoading -> CircularProgressIndicator(
                        modifier = Modifier.align(
                            Alignment.Center
                        )
                    )

                    uiState.errorMessage != null -> Text(
                        text = uiState.errorMessage ?: stringResource(R.string.error_message),
                        color = MaterialTheme.colorScheme.error,
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier.align(Alignment.Center)
                    )

                    uiState.chartData.isEmpty() -> Text(
                        text = stringResource(R.string.statistics_empty_transaction_message),
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier.align(Alignment.Center)
                    )

                    else -> StatisticsContent(uiState = uiState)
                }
            }
        }
    }
}