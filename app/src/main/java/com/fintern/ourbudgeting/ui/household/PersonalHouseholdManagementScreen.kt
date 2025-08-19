package com.fintern.ourbudgeting.ui.household

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.fintern.ourbudgeting.R
import com.fintern.ourbudgeting.ui.household.component.HouseHoldTopAppbar
import com.fintern.ourbudgeting.ui.save.componenet.CommonOutlinedTextField
import com.fintern.ourbudgeting.ui.user.UserViewModel

@Preview
@Composable
fun PersonalHouseholdManagementScreen(
    viewModel: UserViewModel = hiltViewModel()
) {

    LaunchedEffect(Unit) {
        viewModel.initializeUserHousehold()
    }

    val household by viewModel.household.collectAsStateWithLifecycle()
    val householdName = household!!.name

    var textFieldValue by remember { mutableStateOf(householdName) }

    val isSaveEnabled = textFieldValue.isNotEmpty()

    Scaffold(
        modifier = Modifier.background(Color.White),
        topBar = { HouseHoldTopAppbar() },
        containerColor = Color.White,
        contentWindowInsets = WindowInsets(0.dp)
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(horizontal = 16.dp)
                .fillMaxWidth(),
        ) {
            Text(
                text = stringResource(R.string.label_personal_household),
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(24.dp))

            CommonOutlinedTextField(
                value = textFieldValue,
                onValueChange = { newValue ->
                    textFieldValue = newValue
                },
                label = "",
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Done
            )

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = {
                    // TODO: 수정
                },
                enabled = isSaveEnabled,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp),
                shape = RoundedCornerShape(4.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF964BFF)
                )
            ) {
                Text(
                    text = stringResource(R.string.label_edit),
                    color = Color.White
                )
            }
        }
    }
}