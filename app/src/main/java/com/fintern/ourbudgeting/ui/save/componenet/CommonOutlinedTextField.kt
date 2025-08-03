package com.fintern.ourbudgeting.ui.save.componenet

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue

@Composable
fun CommonOutlinedTextField(
    value: Any,
    onValueChange: (Any) -> Unit,
    label: String,
    modifier: Modifier = Modifier,
    readOnly: Boolean = false,
    trailingIcon: @Composable (() -> Unit)? = null,
    keyboardType: KeyboardType = KeyboardType.Text,
    imeAction: ImeAction = ImeAction.Done,
    singleLine: Boolean = true,
) {
    val commonColors = OutlinedTextFieldDefaults.colors(
        focusedBorderColor = Color(0xFF964BFF),
        unfocusedBorderColor = Color(0xFF964BFF),
        focusedLabelColor = Color(0xFF964BFF),
        cursorColor = Color(0xFF964BFF)
    )

    when (value) {
        is String -> {
            OutlinedTextField(
                value = value,
                onValueChange = { onValueChange(it) },
                label = { Text(label) },
                modifier = modifier.fillMaxWidth(),
                trailingIcon = trailingIcon,
                keyboardOptions = KeyboardOptions(
                    keyboardType = keyboardType,
                    imeAction = imeAction
                ),
                singleLine = singleLine,
                readOnly = readOnly,
                colors = commonColors
            )
        }

        is TextFieldValue -> {
            OutlinedTextField(
                value = value,
                onValueChange = { onValueChange(it) },
                label = { Text(label) },
                modifier = modifier.fillMaxWidth(),
                trailingIcon = trailingIcon,
                keyboardOptions = KeyboardOptions(
                    keyboardType = keyboardType,
                    imeAction = imeAction
                ),
                singleLine = singleLine,
                readOnly = readOnly,
                colors = commonColors
            )
        }
    }
}