package com.fintern.ourbudgeting.ui.assetmanagement.component

import android.graphics.Paint
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun StatisticAssetTwoLineChart(
    modifier: Modifier = Modifier,
    data1: List<Pair<Int, Double>> = emptyList(),
    graphLineColor1: Color = Color.Red,
    pointColor1: Color = Color.Red,
    fillBrush1: Boolean = false,
    drawCircleOnTop1: Boolean = true,
    drawTextOnTop1: Boolean = true,
    pointRadius1: Dp = 4.dp,
    data2: List<Pair<Int, Double>> = emptyList(),
    graphLineColor2: Color = Color.Blue,
    pointColor2: Color = Color.Blue,
    fillBrush2: Boolean = false,
    drawCircleOnTop2: Boolean = true,
    drawTextOnTop2: Boolean = true,
    pointRadius2: Dp = 4.dp,
    spacing: Float = 50f,
    xAxisLabelColor: Color = Color.Black,
    xAxisLabelTextSize: TextUnit = 11.sp,
    yAxisLabelColor: Color = Color.Black,
    yAxisLabelTextSize: TextUnit = 11.sp,
    dataValueTextColor: Color = Color.DarkGray,
    dataValueTextSize: TextUnit = 10.sp,
    yAxisStep: Int = 1, // Y축 라벨 간격
    gridLineColor: Color = Color.LightGray.copy(alpha = 0.5f),
    gridStrokeWidth: Dp = 1.dp,
    graphStrokeWidth: Dp = 2.dp,
    graphStrokeCap: StrokeCap = StrokeCap.Butt,
    showGridLines: Boolean = true,
) {
    val density = LocalDensity.current

    val pointRadiusPx1 = with(density) { pointRadius1.toPx() }
    val pointRadiusPx2 = with(density) { pointRadius2.toPx() }

    val graphStrokeWidthPx = with(density) { graphStrokeWidth.toPx() }

    val allYValues = remember(data1, data2) {
        val values = mutableListOf<Double>()
        if (data1.isNotEmpty()) {
            values.addAll(data1.map { it.second })
        }
        if (data2.isNotEmpty()) {
            values.addAll(data2.map { it.second })
        }
        values
    }

    val upperValue =
        remember(allYValues) { (allYValues.maxOrNull()?.plus(yAxisStep))?.toInt() ?: 0 }
    val lowerValue =
        remember(allYValues) { (allYValues.minOrNull()?.minus(yAxisStep)?.toInt() ?: 0) }

    val xAxisLabelPaint = remember(density, xAxisLabelColor, xAxisLabelTextSize) {
        Paint().apply {
            color = xAxisLabelColor.toArgb()
            textAlign = Paint.Align.CENTER
            textSize = density.run { xAxisLabelTextSize.toPx() }
        }
    }

    val yAxisLabelPaint = remember(density, yAxisLabelColor, yAxisLabelTextSize) {
        Paint().apply {
            color = yAxisLabelColor.toArgb()
            textAlign = Paint.Align.CENTER
            textSize = density.run { yAxisLabelTextSize.toPx() }
        }
    }

    val dataValueTextPaint = remember(density, dataValueTextColor, dataValueTextSize) {
        Paint().apply {
            color = dataValueTextColor.toArgb()
            textAlign = Paint.Align.CENTER
            textSize = density.run { dataValueTextSize.toPx() }
        }
    }

    Canvas(modifier = modifier) {
        val maxDataSize = maxOf(data1.size, data2?.size ?: 0)
        val spacePerXLabel = if (maxDataSize > 0) (size.width - spacing) / maxDataSize else 0f

        (data1.indices step 1).forEach { i ->
            val hour = data1[i].first
            drawContext.canvas.nativeCanvas.apply {
                drawText(
                    hour.toString(),
                    spacing + i * spacePerXLabel,
                    size.height - xAxisLabelPaint.descent(),
                    xAxisLabelPaint
                )
            }
        }

        for (value in lowerValue..upperValue step yAxisStep) {
            val ratio = (value - lowerValue).toFloat() / (upperValue - lowerValue).toFloat()
            val yPos = (size.height - spacing) - (ratio * (size.height - spacing))

            drawContext.canvas.nativeCanvas.apply {
                drawText(
                    value.toString(),
                    spacing - 10.dp.toPx(),
                    yPos + yAxisLabelPaint.textSize / 2,
                    yAxisLabelPaint
                )
            }
        }

        if (showGridLines) {
            for (value in lowerValue..upperValue step yAxisStep) {
                val ratio = (value - lowerValue).toFloat() / (upperValue - lowerValue).toFloat()
                val yPos = size.height - spacing - (ratio * (size.height - spacing))

                drawLine(
                    color = gridLineColor,
                    start = Offset(spacing, yPos),
                    end = Offset(size.width, yPos),
                    strokeWidth = gridStrokeWidth.toPx()
                )
            }

            (data1.indices step 1).forEach { i ->
                val xPos = spacing + i * spacePerXLabel
                drawLine(
                    color = gridLineColor,
                    start = Offset(xPos, 0f),
                    end = Offset(xPos, size.height - spacing),
                    strokeWidth = gridStrokeWidth.toPx()
                )
            }
        }

        drawSingleLineChart(
            data = data1,
            lineColor = graphLineColor1,
            pointColor = pointColor1,
            fillBrush = fillBrush1,
            drawCircle = drawCircleOnTop1,
            drawText = drawTextOnTop1,
            spacing = spacing,
            spacePerXLabel = spacePerXLabel,
            lowerValue = lowerValue,
            upperValue = upperValue,
            pointRadiusPx = pointRadiusPx1,
            graphStrokeWidthPx = graphStrokeWidthPx,
            graphStrokeCap = graphStrokeCap,
            dataValueTextPaint = dataValueTextPaint
        )


        drawSingleLineChart(
            data = data2,
            lineColor = graphLineColor2,
            pointColor = pointColor2,
            fillBrush = fillBrush2,
            drawCircle = drawCircleOnTop2,
            drawText = drawTextOnTop2,
            spacing = spacing,
            spacePerXLabel = spacePerXLabel,
            lowerValue = lowerValue,
            upperValue = upperValue,
            pointRadiusPx = pointRadiusPx2,
            graphStrokeWidthPx = graphStrokeWidthPx,
            graphStrokeCap = graphStrokeCap,
            dataValueTextPaint = dataValueTextPaint,
        )

    }
}

@Preview(showBackground = true)
@Composable
fun StatisticAssetTwoLineChartPreview() {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(),
        color = Color.White,
    ) {
        val sampleData1 = listOf(
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
        )
        val sampleData2 = listOf(
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
        )
        StatisticAssetTwoLineChart(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(),
            data1 = sampleData1,
            data2 = sampleData2,
        )
    }
}