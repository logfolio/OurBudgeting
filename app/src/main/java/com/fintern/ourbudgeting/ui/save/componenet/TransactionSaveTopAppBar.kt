package com.fintern.ourbudgeting.ui.save.componenet

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.fintern.ourbudgeting.R
import com.fintern.ourbudgeting.ui.save.TransactionSaveUiState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TransactionTopAppBar(
    uiState: TransactionSaveUiState,
    onNavigateToBack: () -> Unit,
) {
    TopAppBar(
        title = { Text(stringResource(id = uiState.transactionType.labelRes)) },
        navigationIcon = {
            IconButton(onClick = { onNavigateToBack() }) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = stringResource(R.string.action_back),
                )
            }
        },
        actions = {
            IconButton(onClick = { }) {
                Icon(
                    painter = painterResource(R.drawable.ic_camera),
                    contentDescription = stringResource(R.string.action_back),
                    tint = Color(0xFF964BFF)
                )
            }

            IconButton(onClick = { }) {
                Icon(
                    painter = painterResource(R.drawable.ic_mic),
                    contentDescription = stringResource(R.string.action_back),
                    tint = Color(0xFF964BFF)
                )
            }
        }
    )
}