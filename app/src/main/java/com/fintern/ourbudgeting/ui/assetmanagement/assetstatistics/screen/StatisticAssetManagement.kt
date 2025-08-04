package com.fintern.ourbudgeting.ui.assetmanagement.assetstatistics.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.fintern.ourbudgeting.R
import com.fintern.ourbudgeting.ui.assetmanagement.assetstatistics.component.LineChart
import com.fintern.ourbudgeting.ui.assetmanagement.assetstatistics.component.StatisticAssetChartTitle
import com.fintern.ourbudgeting.ui.assetmanagement.assetstatistics.component.StatisticAssetTopSection
import com.fintern.ourbudgeting.ui.assetmanagement.assetstatistics.component.StatisticsAssetTopAppBar
import com.fintern.ourbudgeting.ui.assetmanagement.assetstatistics.component.TwoLineChart
import com.fintern.ourbudgeting.ui.assetmanagement.data.LineChartConfig
import com.fintern.ourbudgeting.ui.assetmanagement.data.Point

@Composable
fun StatisticAssetManagementScreen(
    modifier: Modifier = Modifier,
    totalPrice: Long,
    data: List<Point>,
    firstData: List<Point>,
    secondData: List<Point>
) {
    Scaffold(
        modifier = modifier.background(Color.White),
        topBar = { StatisticsAssetTopAppBar() }
    ) { paddingValue ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .padding(paddingValue)
        ) {
            StatisticAssetTopSection(totalPrice = totalPrice)
            StatisticAssetChartTitle(title = stringResource(R.string.cumulative_balance))
            LineChart(
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .fillMaxWidth()
                    .height(250.dp),
                line = LineChartConfig(
                    data = data,
                    graphLineColor = Color.Blue,
                    pointColor = Color.Gray
                )
            )
            StatisticAssetChartTitle(title = stringResource(R.string.income_expense))
            TwoLineChart(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .height(250.dp),
                firstLine = LineChartConfig(
                    data = firstData,
                    graphLineColor = Color.Blue,
                    pointColor = Color.Blue,
                    fillBrush = false
                ),
                secondLine = LineChartConfig(
                    data = secondData,
                    graphLineColor = Color.Red,
                    pointColor = Color.Red,
                    fillBrush = false
                )
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun StatisticAssetManagementScreenPreview() {
    MaterialTheme {
        StatisticAssetManagementScreen(
            data = listOf(
                Point(1, 10.0),
                Point(2, 15.0),
                Point(3, 7.0),
                Point(4, 20.0),
                Point(5, 12.0),
                Point(6, 25.0),
                Point(7, 18.0),
                Point(8, 10.0),
                Point(9, 15.0),
                Point(10, 7.0),
                Point(11, 20.0),
                Point(12, 12.0)
            ),
            firstData = listOf(
                Point(1, 10.0),
                Point(2, 15.0),
                Point(3, 7.0),
                Point(4, 20.0),
                Point(5, 12.0),
                Point(6, 25.0),
                Point(7, 18.0),
                Point(8, 10.0),
                Point(9, 15.0),
                Point(10, 7.0),
                Point(11, 20.0),
                Point(12, 12.0)
            ),
            secondData = listOf(
                Point(1, 12.0),
                Point(2, 14.0),
                Point(3, 10.0),
                Point(4, 25.0),
                Point(5, 21.0),
                Point(6, 20.0),
                Point(7, 15.0),
                Point(8, 18.0),
                Point(9, 8.0),
                Point(10, 12.0),
                Point(11, 15.0),
                Point(12, 17.0)
            ),
            totalPrice = 50000,
        )
    }
}