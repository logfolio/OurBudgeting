package com.fintern.ourbudgeting.ui.statistics.chart.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.tooling.preview.Preview
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

@Preview(showBackground = true)
@Composable
fun Preview() {
    PieChart(
        data = listOf(
            PieEntry(30f, "식비", Color(0xFFFF6B6B)),
            PieEntry(10f, "교통", Color(0xFFA29BFE)),
            PieEntry(25f, "카페", Color(0xFF4ECDC4)),
            PieEntry(20f, "쇼핑", Color(0xFF45B7D1)),
            PieEntry(15f, "의료", Color(0xFF96CEB4)),
            PieEntry(10f, "교육", Color(0xFFFFEAA7))
        )
    )
}