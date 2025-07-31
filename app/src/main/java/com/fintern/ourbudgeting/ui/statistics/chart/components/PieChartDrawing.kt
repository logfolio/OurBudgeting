package com.fintern.ourbudgeting.ui.statistics.chart.components

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.text.TextMeasurer
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.fintern.ourbudgeting.ui.statistics.chart.model.ChartConfig
import com.fintern.ourbudgeting.ui.statistics.chart.model.ChartData
import com.fintern.ourbudgeting.data.chart.PieEntry
import java.lang.Math.toRadians
import kotlin.math.cos
import kotlin.math.sin

/**
 * 파이차트의 각 조각들을 그림
 *
 * @param data 파이차트 원본 데이터 리스트
 * @param chartData 미리 계산된 차트 데이터 (총합, 백분율 등)
 * @param config 차트 설정값 (반지름, 크기 등)
 */
internal fun DrawScope.drawPieSlices(
    data: List<PieEntry>,
    chartData: ChartData,
    config: ChartConfig
) {
    var startAngle = 270f

    data.forEach { entry ->
        val sweepAngle = (entry.value / chartData.totalValue) * 360f

        drawArc(
            color = entry.pieColor,
            startAngle = startAngle,
            sweepAngle = sweepAngle,
            useCenter = true,
            topLeft = Offset(
                center.x - config.chartRadiusPx,
                center.y - config.chartRadiusPx
            ),
            size = androidx.compose.ui.geometry.Size(
                config.chartRadiusPx * 2,
                config.chartRadiusPx * 2
            )
        )

        startAngle += sweepAngle
    }
}

/**
 * 파이차트의 각 조각에 대한 라벨과 연결선을 그림
 *
 * @param data 파이차트 원본 데이터 리스트
 * @param chartData 미리 계산된 차트 데이터 (각도, 포맷된 백분율 등)
 * @param config 차트 설정값
 * @param textMeasurer 텍스트 크기 측정을 위한 객체
 */
internal fun DrawScope.drawLabelsWithLines(
    data: List<PieEntry>,
    chartData: ChartData,
    config: ChartConfig,
    textMeasurer: TextMeasurer
) {
    data.forEachIndexed { index, entry ->
        val angle = chartData.angles[index]
        val formattedPercentage = chartData.formattedPercentages[index]
        val text = "${entry.label}\n${formattedPercentage}%"

        drawLabelWithLine(
            text = text,
            angle = angle,
            innerRadius = config.chartRadiusPx,
            outerRadius = config.chartRadiusPx * 1.2f, // labelDistanceRatio
            color = entry.pieColor,
            textMeasurer = textMeasurer
        )
    }
}

/**
 * 파이 차트 중앙에 원을 그림
 *
 * @param radius 중앙 원의 반지름
 * @param center 원을 그릴 중심 좌표
 */
internal fun DrawScope.drawCenterHole(radius: Float, center: Offset) {
    drawCircle(
        color = Color.White,
        radius = radius,
        center = center
    )
}

/**
 * 지정된 각도와 위치에 라벨과 연결선을 그림
 *
 * @param text 표시할 텍스트 (라벨명과 백분율 포함)
 * @param angle 연결선이 향할 각도
 * @param innerRadius 연결선 시작점까지의 거리
 * @param outerRadius 연결선 끝점까지의 거리
 * @param color 연결선 색상
 * @param textMeasurer 텍스트 크기 측정 객체
 */
internal fun DrawScope.drawLabelWithLine(
    text: String,
    angle: Float,
    innerRadius: Float,
    outerRadius: Float,
    color: Color,
    textMeasurer: TextMeasurer,
) {
    val angleInRadians = toRadians(angle.toDouble()).toFloat()

    // 선의 시작점과 끝점 계산
    val lineStart = Offset(
        center.x + innerRadius * cos(angleInRadians),
        center.y + innerRadius * sin(angleInRadians)
    )
    val lineEnd = Offset(
        center.x + outerRadius * cos(angleInRadians),
        center.y + outerRadius * sin(angleInRadians)
    )

    // 선 그리기
    drawLine(
        color = color,
        start = lineStart,
        end = lineEnd,
        strokeWidth = 2.dp.toPx()
    )

    // 텍스트 그리기
    drawTextLabel(
        text = text,
        angle = angle,
        lineEnd = lineEnd,
        textMeasurer = textMeasurer
    )
}

/**
 * 연결선 끝에 텍스트 라벨을 그림
 *
 * @param text 표시할 텍스트 (줄바꿈 문자로 여러 줄 지원)
 * @param angle 텍스트 위치를 결정하는 각도 (정렬 방향 계산용)
 * @param lineEnd 연결선의 끝점 좌표 (텍스트 기준점)
 * @param textMeasurer 텍스트 크기 측정 객체
 */
internal fun DrawScope.drawTextLabel(
    text: String,
    angle: Float,
    lineEnd: Offset,
    textMeasurer: TextMeasurer
) {
    val textStyle = TextStyle(
        fontSize = 14.sp,
        color = Color.Black,
        fontWeight = FontWeight.Normal
    )

    val lines = text.split("\n")
    val textPadding = 8.dp.toPx()

    lines.forEachIndexed { index, line ->
        val textLayoutResult = textMeasurer.measure(line, textStyle)
        val textWidth = textLayoutResult.size.width
        val textHeight = textLayoutResult.size.height

        // 텍스트 위치 계산 (각도에 따라 좌우 정렬)
        val textX = if (angle > 90f && angle < 270f) {
            lineEnd.x - textPadding - textWidth // 왼쪽 반원: 오른쪽 정렬
        } else {
            lineEnd.x + textPadding // 오른쪽 반원: 왼쪽 정렬
        }

        // 여러 줄 텍스트의 세로 중앙 정렬
        val textY = lineEnd.y + (index - lines.size / 2f + 0.5f) * textHeight

        drawText(
            textLayoutResult = textLayoutResult,
            topLeft = Offset(textX, textY - textHeight / 2)
        )
    }
}