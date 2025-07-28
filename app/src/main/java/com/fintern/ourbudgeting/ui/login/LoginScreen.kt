package com.fintern.ourbudgeting.ui.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.fintern.ourbudgeting.R
import com.fintern.ourbudgeting.ui.theme.OurBudgetingTheme

@Composable
fun LoginScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Text("가계부  쓰고 가게", style = MaterialTheme.typography.displayMedium)
        Spacer(modifier = Modifier.height(200.dp))
        Image(
            painter = painterResource(R.drawable.ic_google_login),
            contentDescription = "로그인 버튼"
        )
    }
}

@Preview(showBackground = true)
@Composable
fun LoginScreenPreview() {
    OurBudgetingTheme {
        LoginScreen()
    }
}