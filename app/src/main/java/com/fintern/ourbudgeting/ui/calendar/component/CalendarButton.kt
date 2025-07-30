package com.fintern.ourbudgeting.ui.calendar.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.fintern.ourbudgeting.R

@Composable
fun CustomIconButton(
    imageVector: ImageVector,
    modifier: Modifier,
    contentDescription: String,
    onClick: () -> Unit = {}
) {
    IconButton(
        onClick = onClick,
        modifier = modifier
            .wrapContentSize()
            .clip(CircleShape)
    ) {
        Icon(
            modifier = Modifier.padding(8.dp),
            imageVector = imageVector,
            tint = LocalContentColor.current,
            contentDescription = contentDescription
        )
    }
}

@Composable
fun CustomDropDownButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    fontSize: TextUnit = 16.sp,
    shape: Shape = RoundedCornerShape(4.dp),
    icon: ImageVector = Icons.Default.ArrowDropDown,
    contentDescription: String = stringResource(R.string.description_select_button)
) {
    Surface(
        onClick = onClick,
        shape = shape,
        color = Color(0xFFEAEAEA),
        shadowElevation = 0.dp,
        modifier = modifier
            .wrapContentHeight()
            .defaultMinSize(minHeight = 0.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = text,
                fontSize = fontSize,
                fontWeight = FontWeight.Bold,
                modifier = modifier.weight(1f),
                textAlign = TextAlign.Center,
                color = Color.Black
            )

            Icon(
                imageVector = icon,
                contentDescription = contentDescription,
                tint = Color.Black,
                modifier = Modifier
                    .padding(start = 4.dp)
            )
        }
    }
}

@Composable
fun CustomFilterButton(
    onClick: () -> Unit,
    icon: ImageVector = Icons.Default.FilterList,
    contentDescription: String = stringResource(R.string.description_filter_button),
    tint: Color = Color(0xff964BFF),
    shape: Shape = CircleShape
) {
    Box(
        modifier = Modifier
            .size(24.dp)
            .clip(shape)
            .clickable(onClick = onClick),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            imageVector = icon,
            contentDescription = contentDescription,
            tint = tint,
            modifier = Modifier.size(24.dp)
        )
    }
}