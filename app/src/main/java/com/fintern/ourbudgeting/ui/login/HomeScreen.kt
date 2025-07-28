package com.fintern.ourbudgeting.ui.login

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun HomeScreen() {
    Scaffold { innerPadding ->
        Text(
            text = "홈 화면",
            modifier = Modifier.padding(innerPadding)
        )
    }
}