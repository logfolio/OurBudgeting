package com.fintern.ourbudgeting.ui.statistics.chart.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.TextMeasurer
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.fintern.ourbudgeting.ui.statistics.chart.model.ChartConfig
import com.fintern.ourbudgeting.ui.statistics.chart.model.ChartData
import com.fintern.ourbudgeting.data.chart.PieEntry

/**
 * 파이 차트 렌더링에 필요한 설정 값들을 계산하여 ChartConfig 객체 생성
 *
 * @param data 파이 차트에 표시할 데이터 리스트
 * @param size 차트 본체의 크기 (지름)
 * @param labelDistanceRatio 차트 중심에서 라벨까지의 거리 비율 (1.0 = 차트 반지름과 같음)
 * @param textMeasurer 텍스트 크기 측정을 위한 TextMeasurer 객체
 * @return 계산된 차트 설정 값들을 담은 ChartConfig 객체
 */
@Composable
internal fun calculateChartConfig(
    data: List<PieEntry>,
    size: Dp,
    labelDistanceRatio: Float,
    textMeasurer: TextMeasurer
): ChartConfig {
    val chartRadiusPx = with(LocalDensity.current) { size.toPx() / 2 }

    val textStyle = TextStyle(
        fontSize = 14.sp,
        color = Color.Black,
        fontWeight = FontWeight.Normal
    )

    val maxTextWidth = with(LocalDensity.current) {
        calculateMaxTextWidth(data, textMeasurer, textStyle).toDp()
    }

    val textPadding = 8.dp
    val lineExtension = with(LocalDensity.current) {
        (chartRadiusPx * (labelDistanceRatio - 1f)).toDp()
    }

    val totalCanvasSize = size + (lineExtension + maxTextWidth + textPadding) * 2

    return ChartConfig(
        chartRadiusPx = chartRadiusPx,
        totalCanvasSize = totalCanvasSize,
        maxTextWidth = maxTextWidth,
        textPadding = textPadding,
        lineExtension = lineExtension
    )
}

/**
 * 파이차트 라벨 텍스트들 중에서 가장 긴 텍스트의 너비 계산
 *
 * @param data 파이차트에 표시할 데이터 리스트
 * @param textMeasurer 텍스트 크기 측정을 위한 TextMeasurer 객체
 * @param textStyle 텍스트 렌더링에 사용할 스타일 (폰트 크기, 굵기 등)
 * @return 가장 긴 텍스트 줄의 너비 (픽셀 단위), 데이터가 없으면 0
 */
internal fun calculateMaxTextWidth(
    data: List<PieEntry>,
    textMeasurer: TextMeasurer,
    textStyle: TextStyle
): Int {
    val totalValue = data.sumOf { it.value.toDouble() }.toFloat()

    return data.maxOfOrNull { entry ->
        val percentage = (entry.value / totalValue * 100)
        val formattedPercentage = "%.1f".format(percentage)
        val text = "${entry.label}\n${formattedPercentage}%"

        text.split("\n").maxOfOrNull { line ->
            textMeasurer.measure(line, textStyle).size.width
        } ?: 0
    } ?: 0
}

/**
 * 파이차트 렌더링에 필요한 데이터들을 미리 계산하여 ChartData 객체 생성
 *
 * @param data 파이차트에 표시할 원본 데이터 리스트
 * @return 계산된 차트 데이터를 담은 ChartData 객체
 */
internal fun prepareChartData(data: List<PieEntry>): ChartData {
    val totalValue = data.sumOf { it.value.toDouble() }.toFloat()

    val percentages = data.map { (it.value / totalValue * 100) }
    val formattedPercentages = percentages.map { "%.1f".format(it) }

    val angles = mutableListOf<Float>()
    var currentAngle = 270f // 12시 방향부터 시작

    data.forEach { entry ->
        val sweepAngle = (entry.value / totalValue) * 360f
        val midAngle = (currentAngle + sweepAngle / 2f) % 360f
        angles.add(midAngle)
        currentAngle += sweepAngle
    }

    return ChartData(
        totalValue = totalValue,
        percentages = percentages,
        formattedPercentages = formattedPercentages,
        angles = angles
    )
}