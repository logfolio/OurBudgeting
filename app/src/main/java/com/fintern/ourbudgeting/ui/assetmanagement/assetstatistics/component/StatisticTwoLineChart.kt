package com.fintern.ourbudgeting.ui.assetmanagement.assetstatistics.component

import android.graphics.Paint
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import com.fintern.ourbudgeting.ui.assetmanagement.data.LineChartConfig
import com.fintern.ourbudgeting.ui.assetmanagement.data.LineChartStyleConfig
import com.fintern.ourbudgeting.ui.assetmanagement.data.Point

@Composable
fun StatisticTwoLineChart(
    modifier: Modifier = Modifier,
    firstLine: LineChartConfig,
    secondLine: LineChartConfig,
    lineChartStyleConfig: LineChartStyleConfig = LineChartStyleConfig()
) {
    val density = LocalDensity.current

    val pointRadiusPx1 = with(density) { firstLine.pointRadius.toPx() }
    val pointRadiusPx2 = with(density) { secondLine.pointRadius.toPx() }

    val graphStrokeWidthPx = with(density) { lineChartStyleConfig.graphStrokeWidth.toPx() }

    val allYValues = remember(firstLine.data, secondLine.data) {
        val values = mutableListOf<Double>()
        if (firstLine.data.isNotEmpty()) {
            values.addAll(firstLine.data.map { it.y })
        }
        if (secondLine.data.isNotEmpty()) {
            values.addAll(secondLine.data.map { it.y })
        }
        values
    }

    val upperValue =
        remember(allYValues) {
            (allYValues.maxOrNull()?.plus(lineChartStyleConfig.yAxisStep))?.toInt() ?: 0
        }
    val lowerValue =
        remember(allYValues) {
            (allYValues.minOrNull()?.minus(lineChartStyleConfig.yAxisStep)?.toInt() ?: 0)
        }

    val xAxisLabelPaint = remember(
        density,
        lineChartStyleConfig.xAxisLabelColor,
        lineChartStyleConfig.xAxisLabelTextSize
    ) {
        Paint().apply {
            color = lineChartStyleConfig.xAxisLabelColor.toArgb()
            textAlign = Paint.Align.CENTER
            textSize = density.run { lineChartStyleConfig.xAxisLabelTextSize.toPx() }
        }
    }

    val yAxisLabelPaint = remember(
        density,
        lineChartStyleConfig.yAxisLabelColor,
        lineChartStyleConfig.yAxisLabelTextSize
    ) {
        Paint().apply {
            color = lineChartStyleConfig.yAxisLabelColor.toArgb()
            textAlign = Paint.Align.CENTER
            textSize = density.run { lineChartStyleConfig.yAxisLabelTextSize.toPx() }
        }
    }

    val dataValueTextPaint = remember(
        density,
        lineChartStyleConfig.dataValueTextColor,
        lineChartStyleConfig.dataValueTextSize
    ) {
        Paint().apply {
            color = lineChartStyleConfig.dataValueTextColor.toArgb()
            textAlign = Paint.Align.CENTER
            textSize = density.run { lineChartStyleConfig.dataValueTextSize.toPx() }
        }
    }
    val baseData =
        listOfNotNull(firstLine.data, secondLine.data).maxByOrNull { it.size } ?: emptyList()

    Canvas(modifier = modifier) {
        val maxDataSize = maxOf(firstLine.data.size, secondLine.data.size)
        val spacePerXLabel =
            if (maxDataSize > 0) (size.width - lineChartStyleConfig.spacing) / maxDataSize else 0f

        drawXAxisLabels(baseData, lineChartStyleConfig.spacing, spacePerXLabel, xAxisLabelPaint)
        drawYAxisLabels(
            lowerValue,
            upperValue,
            lineChartStyleConfig.yAxisStep,
            size.height,
            lineChartStyleConfig.spacing,
            yAxisLabelPaint
        )

        if (lineChartStyleConfig.showGridLines) {
            drawHorizontalGridLines(
                lowerValue,
                upperValue,
                lineChartStyleConfig.spacing,
                lineChartStyleConfig.gridLineColor,
                lineChartStyleConfig.gridStrokeWidth.toPx()
            )
            drawVerticalGridLines(
                baseData.size,
                lineChartStyleConfig.spacing,
                spacePerXLabel,
                lineChartStyleConfig.gridLineColor,
                lineChartStyleConfig.gridStrokeWidth.toPx()
            )
        }

        drawSingleLineChart(
            line = firstLine,
            spacing = lineChartStyleConfig.spacing,
            spacePerXLabel = spacePerXLabel,
            lowerValue = lowerValue,
            upperValue = upperValue,
            pointRadiusPx = pointRadiusPx1,
            graphStrokeWidthPx = graphStrokeWidthPx,
            graphStrokeCap = lineChartStyleConfig.graphStrokeCap,
            dataValueTextPaint = dataValueTextPaint
        )

        drawSingleLineChart(
            line = secondLine,
            spacing = lineChartStyleConfig.spacing,
            spacePerXLabel = spacePerXLabel,
            lowerValue = lowerValue,
            upperValue = upperValue,
            pointRadiusPx = pointRadiusPx2,
            graphStrokeWidthPx = graphStrokeWidthPx,
            graphStrokeCap = lineChartStyleConfig.graphStrokeCap,
            dataValueTextPaint = dataValueTextPaint
        )
    }
}

@Preview(showBackground = true)
@Composable
fun StatisticTwoLineChartPreview() {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(),
        color = Color.White,
    ) {
        val customStyle = LineChartStyleConfig(
            xAxisLabelColor = Color.Gray
        )
        val sampleData1 = LineChartConfig(
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
            graphLineColor = Color.Red,
            pointColor = Color.Red,
        )

        val sampleData2 = LineChartConfig(
            data = listOf(
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
            graphLineColor = Color.Blue,
            pointColor = Color.Blue
        )
        StatisticTwoLineChart(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(),
            firstLine = sampleData1,
            secondLine = sampleData2,
            lineChartStyleConfig = customStyle
        )
    }
}