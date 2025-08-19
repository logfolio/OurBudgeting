package com.fintern.ourbudgeting.ui.assetmanagement.assettypeaddition.component

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun AssetAdditionBody(
    modifier: Modifier = Modifier,
    label: String,
    placeHolder: String,
    value: String,
    onValueChanged: (String) -> Unit
) {
    OutlinedTextField(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 6.dp),
        value = value,
        onValueChange = onValueChanged,
        label = {
            Text(label, color = Color.Gray)
        },
        placeholder = { Text(placeHolder, color = Color.Gray) },
        colors = OutlinedTextFieldDefaults.colors(
            focusedTextColor = Color.Black,
            unfocusedTextColor = Color.Black,
            focusedBorderColor = Color(0xFF964BFF),
            unfocusedBorderColor = Color(0xFF964BFF)
        )
    )
}

@Preview(showBackground = true)
@Composable
fun AddAssetOutLinedTextFieldPreview() {
    MaterialTheme {
        AssetAdditionBody(
            label = "금액",
            placeHolder = "금액을 입력해주세요.",
            value = "",
            onValueChanged = {})
    }
}
