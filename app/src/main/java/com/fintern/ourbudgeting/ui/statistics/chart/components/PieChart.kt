package com.fintern.ourbudgeting.ui.statistics.chart.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.fintern.ourbudgeting.data.chart.PieEntry

@Composable
fun PieChart(
    data: List<PieEntry>,
    modifier: Modifier = Modifier,
    size: Dp = 240.dp,
    holeRatio: Float = 0.5f,
    labelDistanceRatio: Float = 1.2f,
) {
    val textMeasurer = rememberTextMeasurer()

    val config = calculateChartConfig(
        data = data,
        size = size,
        labelDistanceRatio = labelDistanceRatio,
        textMeasurer = textMeasurer
    )

    Canvas(modifier = modifier.size(config.totalCanvasSize)) {
        val chartData = prepareChartData(data)

        drawPieSlices(
            data = data,
            chartData = chartData,
            config = config
        )

        drawLabelsWithLines(
            data = data,
            chartData = chartData,
            config = config,
            textMeasurer = textMeasurer
        )

        drawCenterHole(
            radius = config.chartRadiusPx * holeRatio,
            center = center
        )
    }
}