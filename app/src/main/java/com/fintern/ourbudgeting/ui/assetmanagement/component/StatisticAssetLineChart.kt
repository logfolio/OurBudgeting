package com.fintern.ourbudgeting.ui.assetmanagement.component

import android.graphics.Paint
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlin.math.roundToInt


fun DrawScope.drawSingleLineChart(
    data: List<Pair<Int, Double>>,
    lineColor: Color,
    pointColor: Color,
    fillBrush: Boolean,
    drawCircle: Boolean,
    drawText: Boolean,
    spacing: Float,
    spacePerXLabel: Float,
    lowerValue: Int,
    upperValue: Int,
    pointRadiusPx: Float,
    graphStrokeWidthPx: Float,
    graphStrokeCap: StrokeCap,
    dataValueTextPaint: Paint,
) {
    val graphDrawingHeight = size.height - spacing

    val strokePath = Path().apply {
        if (data.isEmpty()) return

        data.indices.forEach { i ->
            val info = data[i]
            val ratio = (info.second - lowerValue).toFloat() / (upperValue - lowerValue).toFloat()
            val x = spacing + i * spacePerXLabel
            val y = graphDrawingHeight - (ratio * graphDrawingHeight)
            if (i == 0) {
                moveTo(x, y)
            }
            lineTo(x, y)
        }
    }

    drawPath(
        path = strokePath,
        color = lineColor,
        style = Stroke(
            width = graphStrokeWidthPx,
            cap = graphStrokeCap
        )
    )

    if (fillBrush) {
        val fillPath = Path().apply {
            addPath(strokePath)
            if (data.isNotEmpty()) {
                val lastX = spacing + (data.size - 1) * spacePerXLabel
                lineTo(lastX, size.height - spacing)
                lineTo(spacing, size.height - spacing)
            }
            close()
        }

        val areaFillBrush = Brush.verticalGradient(
            colors = listOf(
                lineColor.copy(alpha = 0.5f),
                Color.Transparent
            )
        )
        drawPath(
            path = fillPath,
            brush = areaFillBrush
        )
    }

    data.indices.forEach { i ->
        val info = data[i]
        val ratio = (info.second - lowerValue).toFloat() / (upperValue - lowerValue).toFloat()
        val xPos = spacing + i * spacePerXLabel
        val yPos = graphDrawingHeight - (ratio * graphDrawingHeight)

        if (drawCircle) {
            drawCircle(
                color = pointColor,
                center = Offset(xPos, yPos),
                radius = pointRadiusPx
            )
        }

        if (drawText) {
            drawContext.canvas.nativeCanvas.apply {
                drawText(
                    info.second.roundToInt().toString(), // 값을 정수로 반올림하여 표시
                    xPos,
                    yPos - pointRadiusPx - 15f, // 원 위쪽에 표시 + 여유 공간
                    dataValueTextPaint
                )
            }
        }
    }
}

@Composable
fun StatisticAssetLineChart(
    modifier: Modifier = Modifier,
    data: List<Pair<Int, Double>> = emptyList(),
    spacing: Float = 50f,
    xAxisLabelColor: Color = Color.Black,
    xAxisLabelTextSize: TextUnit = 11.sp,
    yAxisLabelColor: Color = Color.Black,
    yAxisLabelTextSize: TextUnit = 11.sp,
    yAxisStep: Int = 1,
    gridLineColor: Color = Color.LightGray.copy(alpha = 0.5f),
    gridStrokeWidth: Dp = 1.dp,
    graphStrokeWidth: Dp = 2.dp,
    graphStrokeCap: StrokeCap = StrokeCap.Butt,
    dataValueTextColor: Color = Color.Gray,
    dataValueTextSize: TextUnit = 10.sp,
    showGridLines: Boolean = true,
    graphLineColor: Color = Color.Black,
    pointColor: Color = Color.Gray,
    pointRadius: Dp = 4.dp,
    fillBrush: Boolean = false,
    drawCircleOnTop: Boolean = true,
    drawTextOnTop: Boolean = true
) {
    val density = LocalDensity.current

    val pointRadiusPx = with(density) { pointRadius.toPx() }
    val graphStrokeWidthPx = with(density) { graphStrokeWidth.toPx() }

    val upperValue = remember { (data.maxOfOrNull { it.second }?.plus(1))?.toInt() ?: 0 }
    val lowerValue = remember { (data.minOfOrNull { it.second }?.minus(1)?.toInt() ?: 0) }

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
        val spacePerXLabel = (size.width - spacing) / data.size

        (data.indices step 1).forEach { i ->
            val hour = data[i].first
            drawContext.canvas.nativeCanvas.apply {
                drawText(
                    hour.toString(),
                    spacing + i * spacePerXLabel,
                    size.height, // ??
                    xAxisLabelPaint
                )
            }
        }

        (lowerValue..upperValue step yAxisStep).forEach { value ->
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
            (lowerValue..upperValue).forEach { value ->
                val ratio = (value - lowerValue).toFloat() / (upperValue - lowerValue).toFloat()
                val yPos = size.height - spacing - (ratio * (size.height - spacing))

                drawLine(
                    color = gridLineColor,
                    start = Offset(spacing, yPos),
                    end = Offset(size.width, yPos),
                    strokeWidth = gridStrokeWidth.toPx()
                )
            }

            (data.indices step 1).forEach { i ->
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
            data = data,
            lineColor = graphLineColor,
            pointColor = pointColor,
            fillBrush = fillBrush,
            drawCircle = drawCircleOnTop,
            drawText = drawTextOnTop,
            spacing = spacing,
            spacePerXLabel = spacePerXLabel,
            lowerValue = lowerValue,
            upperValue = upperValue,
            pointRadiusPx = pointRadiusPx,
            graphStrokeWidthPx = graphStrokeWidthPx,
            graphStrokeCap = graphStrokeCap,
            dataValueTextPaint = dataValueTextPaint
        )
    }
}

@Preview(showBackground = true)
@Composable
fun StatisticAssetLineChartPreview() {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(),
        color = Color.White,
    ) {
        val sampleData = listOf(
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
        StatisticAssetLineChart(
            modifier = Modifier
                .fillMaxWidth()
                .height(250.dp),
            data = sampleData,
            graphLineColor = Color.Blue,
            pointColor = Color.Blue,
            fillBrush = true,
            drawTextOnTop = true
        )
    }
}