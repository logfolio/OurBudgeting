package com.fintern.ourbudgeting.ui.statistics.chart.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.fintern.ourbudgeting.R
import com.fintern.ourbudgeting.ui.statistics.chart.StatisticsUiState
import kotlin.math.roundToInt

@Composable
fun StatisticsContent(
    uiState: StatisticsUiState,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
    ) {
        PieChart(
            data = uiState.chartData,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )

        Column(modifier = Modifier.padding(top = 16.dp)) {
            uiState.chartData.forEach { entry ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Surface(
                        color = entry.pieColor,
                        contentColor = Color.White,
                        shape = RoundedCornerShape(50),
                    ) {
                        Text(
                            text = entry.pieLabel,
                            modifier = Modifier
                                .padding(horizontal = 8.dp, vertical = 4.dp),
                            style = MaterialTheme.typography.titleMedium
                        )
                    }

                    Spacer(modifier = Modifier.weight(1f))

                    val total = uiState.chartData.sumOf { it.value.toDouble() }
                    val percent = if (total > 0) {
                        ((entry.value / total * 100 * 10).roundToInt() / 10.0)
                    } else 0.0

                    Text(
                        text = stringResource(R.string.percentage_format, percent),
                        modifier = Modifier.padding(end = 8.dp),
                        style = MaterialTheme.typography.titleMedium
                    )

                    Text(
                        text = stringResource(R.string.won_format).format(entry.value.toInt()),
                        style = MaterialTheme.typography.titleMedium
                    )
                }
            }
        }
    }
}