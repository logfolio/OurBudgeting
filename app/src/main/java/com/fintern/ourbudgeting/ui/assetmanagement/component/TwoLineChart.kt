package com.fintern.ourbudgeting.ui.assetmanagement.component

import android.graphics.Paint
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.fintern.ourbudgeting.ui.assetmanagement.data.Point

@Composable
fun TwoLineChart(
    modifier: Modifier = Modifier,
    firstData: List<Point> = emptyList(),
    graphLineColor1: Color = Color.Red,
    pointColor1: Color = Color.Red,
    fillBrush1: Boolean = false,
    drawCircleOnTop1: Boolean = true,
    drawTextOnTop1: Boolean = true,
    pointRadius1: Dp = 4.dp,
    secondData: List<Point>? = null,
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
    yAxisStep: Int = 1,
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

    val allYValues = remember(firstData, secondData) {
        val values = mutableListOf<Double>()
        if (firstData.isNotEmpty()) {
            values.addAll(firstData.map { it.y })
        }
        if (!secondData.isNullOrEmpty()) {
            values.addAll(secondData.map { it.y })
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
    val baseData = listOfNotNull(firstData, secondData).maxByOrNull { it.size } ?: emptyList()

    Canvas(modifier = modifier) {
        val maxDataSize = maxOf(firstData.size, secondData?.size ?: 0)
        val spacePerXLabel = if (maxDataSize > 0) (size.width - spacing) / maxDataSize else 0f

        drawXAxisLabels(baseData, spacing, spacePerXLabel, xAxisLabelPaint)
        drawYAxisLabels(lowerValue, upperValue, yAxisStep, size.height, spacing, yAxisLabelPaint)

        if (showGridLines) {
            drawHorizontalGridLines(
                lowerValue,
                upperValue,
                spacing,
                gridLineColor,
                gridStrokeWidth.toPx()
            )
            drawVerticalGridLines(
                baseData.size,
                spacing,
                spacePerXLabel,
                gridLineColor,
                gridStrokeWidth.toPx()
            )
        }

        drawSingleLineChart(
            data = firstData,
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

        secondData?.let {
            drawSingleLineChart(
                data = it,
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
}

@Preview(showBackground = true)
@Composable
fun TwoLineChartPreview() {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(),
        color = Color.White,
    ) {
        val sampleData1 = listOf(
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
        )
        val sampleData2 = listOf(
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
        )
        TwoLineChart(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(),
            firstData = sampleData1,
            secondData = sampleData2,
            fillBrush1 = true,
            fillBrush2 = true
        )
    }
}