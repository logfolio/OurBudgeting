package com.fintern.ourbudgeting.ui.assetmanagement.component

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun AddAssetOutLinedTextField(modifier: Modifier = Modifier, label: String, placeHolder: String) {
    var text by remember { mutableStateOf("") }

    OutlinedTextField(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 6.dp),
        value = text,
        onValueChange = { text = it },
        label = {
            Text(label, color = Color.Gray)
        },
        placeholder = { Text(placeHolder, color = Color.Gray) },
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = Color(0xFF964BFF),
            unfocusedBorderColor = Color(0xFF964BFF)
        )
    )
}

@Preview(showBackground = true)
@Composable
fun AddAssetOutLinedTextFieldPreview() {
    MaterialTheme { AddAssetOutLinedTextField(label = "금액", placeHolder = "금액을 입력해주세요.") }
}
