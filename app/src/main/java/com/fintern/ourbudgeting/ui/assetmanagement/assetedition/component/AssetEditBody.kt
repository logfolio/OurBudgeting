package com.fintern.ourbudgeting.ui.assetmanagement.assetedition.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import com.fintern.ourbudgeting.R

@Composable
fun AssetEditBody(
    modifier: Modifier = Modifier,
    name: String,
    editText: String,
    isEditing: Boolean,
    isUpdating: Boolean = false,
    onEditingChange: (Boolean) -> Unit = {},
    onEditTextChange: (String) -> Unit = {},
    onEditComplete: (String, String) -> Unit = { _, _ -> }
) {

    fun handleEditComplete() {
        val trimmedText = editText.trim()
        if (trimmedText.isNotBlank() && trimmedText != name) {
            onEditComplete(name, trimmedText)
        } else {
            onEditingChange(false)
        }
    }

    fun handleEditCancel() {
        onEditingChange(false)
    }

    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        if (isEditing) {
            TextField(
                value = editText,
                onValueChange = onEditTextChange,
                singleLine = true,
                modifier = Modifier
                    .weight(1f),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                    focusedIndicatorColor = MaterialTheme.colorScheme.primary,
                    unfocusedIndicatorColor = Color.Gray,
                    focusedTextColor = Color.Black,
                    unfocusedTextColor = Color.Black
                ),
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(
                    onDone = { handleEditComplete() }
                )
            )
        } else {
            Text(
                text = name,
                color = Color.Black,
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.weight(1f)
            )
        }

        Row {
            if (isEditing) {
                if (isUpdating) {
                    CircularProgressIndicator(
                        modifier = Modifier.padding(8.dp),
                        strokeWidth = 2.dp,
                        color = MaterialTheme.colorScheme.primary
                    )
                } else {
                    IconButton(
                        onClick = { handleEditCancel() }
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.ic_close),
                            contentDescription = stringResource(R.string.cancel),
                            tint = Color.Gray
                        )
                    }
                    IconButton(
                        onClick = { handleEditComplete() },
                        enabled = editText.isNotBlank() && editText.trim() != name
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.ic_check),
                            contentDescription = stringResource(R.string.confirm),
                            tint = if (editText.isNotBlank() && editText.trim() != name)
                                Color.Black else Color.Gray
                        )
                    }
                }
            } else {
                IconButton(
                    onClick = { onEditingChange(true) }
                ) {
                    Icon(
                        painter = painterResource(R.drawable.ic_edit),
                        contentDescription = stringResource(R.string.modify),
                        tint = Color.Black
                    )
                }
            }
        }
    }
}

