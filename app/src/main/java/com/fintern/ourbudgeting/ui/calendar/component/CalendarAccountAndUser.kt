package com.fintern.ourbudgeting.ui.calendar.component

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CalendarAccountAndUser(
    selectedAccount: String,
    selectedUser: String,
    onAccountClick: () -> Unit,
    onUserClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        CustomDropDownButton(
            text = selectedAccount,
            onClick = onAccountClick,
            modifier = Modifier.fillMaxWidth()
                .weight(1f),
        )

        Spacer(modifier = Modifier.weight(1f))

        CustomDropDownButton(
            text = selectedUser,
            onClick = onUserClick,
            modifier = Modifier.fillMaxWidth()
                .weight(1f),
        )
    }
}