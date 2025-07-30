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
import androidx.compose.ui.unit.dp
import com.fintern.ourbudgeting.ui.assetmanagement.data.LineChartConfig
import com.fintern.ourbudgeting.ui.assetmanagement.data.LineChartStyleConfig
import com.fintern.ourbudgeting.ui.assetmanagement.data.Point
import kotlin.math.roundToInt

fun DrawScope.drawSingleLineChart(
    line: LineChartConfig,
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
        if (line.data.isEmpty()) return

        line.data.indices.forEach { i ->
            val info = line.data[i]
            val ratio = (info.y - lowerValue).toFloat() / (upperValue - lowerValue).toFloat()
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
        color = line.graphLineColor,
        style = Stroke(
            width = graphStrokeWidthPx,
            cap = graphStrokeCap
        )
    )

    if (line.fillBrush) {
        val fillPath = Path().apply {
            addPath(strokePath)
            if (line.data.isNotEmpty()) {
                val lastX = spacing + (line.data.size - 1) * spacePerXLabel
                lineTo(lastX, size.height - spacing)
                lineTo(spacing, size.height - spacing)
            }
            close()
        }

        val areaFillBrush = Brush.verticalGradient(
            colors = listOf(
                line.graphLineColor.copy(alpha = 0.5f),
                Color.Transparent
            )
        )
        drawPath(
            path = fillPath,
            brush = areaFillBrush
        )
    }

    line.data.indices.forEach { i ->
        val info = line.data[i]
        val ratio = (info.y - lowerValue).toFloat() / (upperValue - lowerValue).toFloat()
        val xPos = spacing + i * spacePerXLabel
        val yPos = graphDrawingHeight - (ratio * graphDrawingHeight)

        if (line.drawCircleOnTop) {
            drawCircle(
                color = line.pointColor,
                center = Offset(xPos, yPos),
                radius = pointRadiusPx
            )
        }

        if (line.drawTextOnTop) {
            drawContext.canvas.nativeCanvas.apply {
                drawText(
                    info.y.roundToInt().toString(),
                    xPos,
                    yPos - pointRadiusPx - 15f,
                    dataValueTextPaint
                )
            }
        }
    }
}

fun DrawScope.drawXAxisLabels(
    data: List<Point>,
    spacing: Float,
    spacer: Float,
    paint: Paint
) {
    data.indices.forEach { i ->
        val hour = data[i].x
        drawContext.canvas.nativeCanvas.apply {
            drawText(
                hour.toString(),
                spacing + i * spacer,
                size.height,
                paint
            )
        }
    }
}


fun DrawScope.drawYAxisLabels(
    lowerValue: Int,
    upperValue: Int,
    yAxisStep: Int,
    sizeHeight: Float,
    spacing: Float,
    paint: Paint
) {
    (lowerValue..upperValue step yAxisStep).forEach { value ->
        val ratio = (value - lowerValue).toFloat() / (upperValue - lowerValue).toFloat()
        val yPos = (sizeHeight - spacing) - (ratio * (sizeHeight - spacing))

        drawContext.canvas.nativeCanvas.apply {
            drawText(
                value.toString(),
                spacing - 10.dp.toPx(),
                yPos + paint.textSize / 2,
                paint
            )
        }
    }
}

fun DrawScope.drawHorizontalGridLines(
    lowerValue: Int,
    upperValue: Int,
    spacing: Float,
    gridLineColor: Color,
    gridStrokeWidthPx: Float
) {
    for (value in lowerValue..upperValue) {
        val ratio = (value - lowerValue).toFloat() / (upperValue - lowerValue).toFloat()
        val yPos = size.height - spacing - (ratio * (size.height - spacing))

        drawLine(
            color = gridLineColor,
            start = Offset(spacing, yPos),
            end = Offset(size.width, yPos),
            strokeWidth = gridStrokeWidthPx
        )
    }
}

fun DrawScope.drawVerticalGridLines(
    dataSize: Int,
    spacing: Float,
    spacePerXLabel: Float,
    gridLineColor: Color,
    gridStrokeWidthPx: Float
) {
    for (i in 0 until dataSize) {
        val xPos = spacing + i * spacePerXLabel

        drawLine(
            color = gridLineColor,
            start = Offset(xPos, 0f),
            end = Offset(xPos, size.height - spacing),
            strokeWidth = gridStrokeWidthPx
        )
    }
}

@Composable
fun LineChart(
    modifier: Modifier = Modifier,
    line: LineChartConfig,
    lineChartStyle: LineChartStyleConfig = LineChartStyleConfig()
) {
    val density = LocalDensity.current

    val pointRadiusPx = with(density) { line.pointRadius.toPx() }
    val graphStrokeWidthPx = with(density) { lineChartStyle.graphStrokeWidth.toPx() }

    val upperValue = remember { (line.data.maxOfOrNull { it.y }?.plus(1))?.toInt() ?: 0 }
    val lowerValue = remember { (line.data.minOfOrNull { it.y }?.minus(1)?.toInt() ?: 0) }

    val xAxisLabelPaint =
        remember(density, lineChartStyle.xAxisLabelColor, lineChartStyle.xAxisLabelTextSize) {
            Paint().apply {
                color = lineChartStyle.xAxisLabelColor.toArgb()
                textAlign = Paint.Align.CENTER
                textSize = density.run { lineChartStyle.xAxisLabelTextSize.toPx() }
            }
        }

    val yAxisLabelPaint =
        remember(density, lineChartStyle.yAxisLabelColor, lineChartStyle.yAxisLabelTextSize) {
            Paint().apply {
                color = lineChartStyle.yAxisLabelColor.toArgb()
                textAlign = Paint.Align.CENTER
                textSize = density.run { lineChartStyle.yAxisLabelTextSize.toPx() }
            }
        }

    val dataValueTextPaint =
        remember(density, lineChartStyle.dataValueTextColor, lineChartStyle.dataValueTextSize) {
            Paint().apply {
                color = lineChartStyle.dataValueTextColor.toArgb()
                textAlign = Paint.Align.CENTER
                textSize = density.run { lineChartStyle.dataValueTextSize.toPx() }
            }
        }

    Canvas(modifier = modifier) {
        val spacePerXLabel = (size.width - lineChartStyle.spacing) / line.data.size

        drawXAxisLabels(
            data = line.data,
            spacing = lineChartStyle.spacing,
            spacer = spacePerXLabel,
            paint = xAxisLabelPaint
        )

        drawYAxisLabels(
            lowerValue = lowerValue,
            upperValue = upperValue,
            yAxisStep = lineChartStyle.yAxisStep,
            sizeHeight = size.height,
            paint = yAxisLabelPaint,
            spacing = lineChartStyle.spacing
        )

        if (lineChartStyle.showGridLines) {
            drawHorizontalGridLines(
                lowerValue = lowerValue,
                upperValue = upperValue,
                spacing = lineChartStyle.spacing,
                gridLineColor = lineChartStyle.gridLineColor,
                gridStrokeWidthPx = lineChartStyle.gridStrokeWidth.toPx()
            )

            drawVerticalGridLines(
                dataSize = line.data.size,
                spacing = lineChartStyle.spacing,
                spacePerXLabel = spacePerXLabel,
                gridLineColor = lineChartStyle.gridLineColor,
                gridStrokeWidthPx = lineChartStyle.gridStrokeWidth.toPx()
            )
        }

        drawSingleLineChart(
            line = line,
            spacing = lineChartStyle.spacing,
            spacePerXLabel = spacePerXLabel,
            lowerValue = lowerValue,
            upperValue = upperValue,
            pointRadiusPx = pointRadiusPx,
            graphStrokeWidthPx = graphStrokeWidthPx,
            graphStrokeCap = lineChartStyle.graphStrokeCap,
            dataValueTextPaint = dataValueTextPaint
        )
    }
}

@Preview(showBackground = true)
@Composable
fun LineChartPreview() {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(),
        color = Color.White,
    ) {
        val sampleLine = LineChartConfig(
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
            graphLineColor = Color.Blue,
            pointColor = Color.Blue,
        )
        LineChart(
            modifier = Modifier
                .fillMaxWidth()
                .height(250.dp),
            line = sampleLine
        )
    }
}