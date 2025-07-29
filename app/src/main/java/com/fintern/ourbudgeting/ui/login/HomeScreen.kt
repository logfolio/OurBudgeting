package com.fintern.ourbudgeting.ui.login

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.fintern.ourbudgeting.ui.user.UserViewModel


@Composable
fun HomeScreen(
    viewModel: UserViewModel = hiltViewModel()
) {
    val uid by viewModel.uid.collectAsState()
    val nickname by viewModel.nickname.collectAsState()

    Scaffold { innerPadding ->
        Column(modifier = Modifier.padding(innerPadding)) {

            Text(
                text = "홈 화면",
                modifier = Modifier.padding(innerPadding)
            )
            Text(
                text = uid,
                modifier = Modifier.padding(innerPadding)
            )
            Text(
                text = nickname,
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}