package com.fintern.ourbudgeting.ui.save.componenet

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.fintern.ourbudgeting.R
import com.fintern.ourbudgeting.ui.save.TransactionSaveUiState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TransactionTopAppBar(
    uiState: TransactionSaveUiState,
    onNavigateToBack: () -> Unit,
    onCameraClick: () -> Unit,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(onClick = { onNavigateToBack() }) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = stringResource(R.string.action_back),
            )
        }

        Text(
            stringResource(id = uiState.transactionType.labelRes),
            style = MaterialTheme.typography.titleLarge
        )

        Spacer(modifier = Modifier.weight(1f))

        IconButton(onClick = onCameraClick) {
            Icon(
                painter = painterResource(R.drawable.ic_camera),
                contentDescription = stringResource(R.string.add_image),
                modifier = Modifier.padding(end = 8.dp),
                tint = Color(0xFF964BFF),
            )
        }

//        IconButton(onClick = { }) {
//            Icon(
//                painter = painterResource(R.drawable.ic_mic),
//                contentDescription = stringResource(R.string.transaction_save_voice),
//                tint = Color(0xFF964BFF)
//            )
//        }
    }
}