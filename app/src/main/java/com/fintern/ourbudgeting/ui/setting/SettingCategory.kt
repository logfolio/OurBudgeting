package com.fintern.ourbudgeting.ui.setting

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun SettingCategory(
    modifier: Modifier = Modifier,
    title: String,
) {
    Text(
        modifier = modifier.padding(bottom = 4.dp),
        text = title,
        fontWeight = FontWeight.Bold
    )
}