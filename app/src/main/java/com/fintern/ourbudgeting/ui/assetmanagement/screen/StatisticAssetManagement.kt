package com.fintern.ourbudgeting.ui.assetmanagement.screen

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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.fintern.ourbudgeting.ui.assetmanagement.component.StatisticAssetChartTitle
import com.fintern.ourbudgeting.ui.assetmanagement.component.StatisticAssetLineChart
import com.fintern.ourbudgeting.ui.assetmanagement.component.StatisticAssetTopSection
import com.fintern.ourbudgeting.ui.assetmanagement.component.StatisticAssetTwoLineChart
import com.fintern.ourbudgeting.ui.assetmanagement.component.StatisticsAssetTopAppBar

@Composable
fun StatisticAssetManagementScreen(
    modifier: Modifier = Modifier,
    totalPrice: Long,
    data: List<Pair<Int, Double>>,
    data1: List<Pair<Int, Double>>,
    data2: List<Pair<Int, Double>>
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
            StatisticAssetChartTitle(title = "누적잔액")
            StatisticAssetLineChart(
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .fillMaxWidth()
                    .height(250.dp),
                data = data,
                graphLineColor = Color.Blue,
                pointColor = Color.Blue,
                fillBrush = true,
                drawTextOnTop = true,
                yAxisLabelTextSize = 8.sp
            )
            StatisticAssetChartTitle(title = "수입/지출")
            StatisticAssetTwoLineChart(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .height(250.dp),
                data1 = data1,
                data2 = data2,
                graphLineColor1 = Color.Blue,
                graphLineColor2 = Color.Red,
                pointColor1 = Color.Blue,
                pointColor2 = Color.Red,
                yAxisLabelTextSize = 8.sp
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
                Pair(1, 10.0),
                Pair(2, 15.0),
                Pair(3, 7.0),
                Pair(4, 20.0),
                Pair(5, 12.0),
                Pair(6, 25.0),
                Pair(7, 18.0),
                Pair(8, 10.0),
                Pair(9, 15.0),
                Pair(10, 7.0),
                Pair(11, 20.0),
                Pair(12, 12.0)
            ),
            data1 = listOf(
                Pair(1, 10.0),
                Pair(2, 15.0),
                Pair(3, 7.0),
                Pair(4, 20.0),
                Pair(5, 12.0),
                Pair(6, 25.0),
                Pair(7, 18.0),
                Pair(8, 10.0),
                Pair(9, 15.0),
                Pair(10, 7.0),
                Pair(11, 20.0),
                Pair(12, 12.0)
            ),
            data2 = listOf(
                Pair(1, 12.0),
                Pair(2, 14.0),
                Pair(3, 10.0),
                Pair(4, 25.0),
                Pair(5, 21.0),
                Pair(6, 20.0),
                Pair(7, 15.0),
                Pair(8, 18.0),
                Pair(9, 8.0),
                Pair(10, 12.0),
                Pair(11, 15.0),
                Pair(12, 17.0)
            ),
            totalPrice = 50000
        )
    }
}