package com.fintern.ourbudgeting.ui.calendar.component

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ContentTransform
import androidx.compose.animation.SizeTransform
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.fintern.ourbudgeting.ui.calendar.component.config.CalendarHeaderConfig
import kotlinx.datetime.Month
import kotlinx.datetime.number

@Composable
fun CalendarHeader(
    month: Month,
    year: Int,
    modifier: Modifier = Modifier,
    calendarHeaderConfig: CalendarHeaderConfig = CalendarHeaderConfig.default(),
    onPreviousClick: () -> Unit = {},
    onNextClick: () -> Unit = {},
) {
    val titleText = remember(year, month) { getTitleText(year, month) }

    HeaderContent(
        titleText = titleText,
        onPreviousClick = onPreviousClick,
        onNextClick = onNextClick,
        calendarHeaderConfig = calendarHeaderConfig,
        modifier = modifier.defaultMinSize(minHeight = 40.dp)
    )
}

@Composable
fun HeaderContent(
    titleText: String,
    onPreviousClick: () -> Unit,
    onNextClick: () -> Unit,
    modifier: Modifier = Modifier,
    calendarHeaderConfig: CalendarHeaderConfig = CalendarHeaderConfig.default()
) {
    var isNext by rememberSaveable { mutableStateOf(true) }
    val paddingModifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)

    Row(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentSize()
            .then(paddingModifier),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        CustomIconButton(
            modifier = Modifier.wrapContentSize(),
            imageVector = Icons.AutoMirrored.Filled.KeyboardArrowLeft,
            contentDescription = "Previous Button",
            onClick = {
                isNext = false
                onPreviousClick()
            }
        )

        AnimatedContent(
            targetState = titleText,
            transitionSpec = {
                addAnimation(isNext = isNext).using(
                    SizeTransform(clip = false)
                )
            }
        ) { month ->
            Text(
                modifier = Modifier
                    .wrapContentSize()
                    .align(Alignment.CenterVertically),
                text = month,
                style = calendarHeaderConfig.textStyle
            )
        }
        CustomIconButton(
            imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
            modifier = Modifier.wrapContentSize(),
            contentDescription = "Next Button",
            onClick = {
                isNext = true
                onNextClick()
            }
        )
    }
}

fun getTitleText(year: Int, month: Month): String {
    val monthValue = month.number

    val yearValue = year.toString()
    return "${yearValue}년${monthValue}월"
}

fun addAnimation(duration: Int = 200, isNext: Boolean): ContentTransform {
    return (
            slideInHorizontally(
                animationSpec = tween(durationMillis = duration)
            ) { height -> if (isNext) height else -height } + fadeIn(
                animationSpec = tween(durationMillis = duration)
            )
            ).togetherWith(
            slideOutHorizontally(
                animationSpec = tween(durationMillis = duration)
            ) { height -> if (isNext) -height else height } + fadeOut(
                animationSpec = tween(durationMillis = duration)
            )
        )
}