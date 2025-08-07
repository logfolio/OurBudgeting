package com.fintern.ourbudgeting.ui.save

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.fintern.ourbudgeting.R
import com.fintern.ourbudgeting.data.model.ExpenseCategoryType
import com.fintern.ourbudgeting.data.model.IncomeCategoryType
import com.fintern.ourbudgeting.ui.common.model.TransactionType
import com.fintern.ourbudgeting.ui.save.componenet.CommonOutlinedTextField
import com.fintern.ourbudgeting.ui.save.componenet.DatePickerField
import com.fintern.ourbudgeting.ui.save.componenet.DropDownField
import com.fintern.ourbudgeting.ui.save.componenet.ImagePreview
import com.fintern.ourbudgeting.ui.save.componenet.TransactionToggle
import com.fintern.ourbudgeting.ui.theme.OurBudgetingTheme

@Composable
fun TransactionAddScreen(
    initialTransactionType: TransactionType,
    modifier: Modifier = Modifier,
    viewModel: TransactionAddViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val launcher =
        rememberLauncherForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
            viewModel.setPhotoUri(uri)
        }

    Scaffold { innerPadding ->
        val categoryOptions = when (uiState.transactionType) {
            TransactionType.EXPENSE -> ExpenseCategoryType.entries
                .map { stringResource(id = it.labelRes) }

            TransactionType.INCOME -> IncomeCategoryType.entries
                .map { stringResource(id = it.labelRes) }
        }

        Column(
            modifier = modifier
                .padding(innerPadding)
                .padding(horizontal = 16.dp)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            TransactionToggle(
                transactionType = uiState.transactionType,
                onTransactionTypeChange = {
                    viewModel.setTransactionType(it)
                }
            )

            DatePickerField(
                label = stringResource(R.string.select_date_label),
                onDateSelected = { transactionDate ->
                    viewModel.setSelectedDate(transactionDate)
                },
                modifier = modifier
            )

            // 자산 선택
            DropDownField(
                label = stringResource(R.string.asset),
                options = listOf("은행 계좌", "현금", "카드"),
                // TODO: Firestore에서 데이터 불러와서 표시
                onOptionSelected = { selected ->
                    viewModel.setSelectedAsset(selected)
                },
            )

            // 카테고리 선택
            DropDownField(
                label = stringResource(R.string.category),
                options = categoryOptions,
                onOptionSelected = { selected ->
                    viewModel.setCategory(selected)
                },
            )

            // 금액 입력
            CommonOutlinedTextField(
                value = uiState.amountTextFieldValue,
                onValueChange = { newValue ->
                    viewModel.setAmountTextFieldValue(newValue)
                },
                label = stringResource(R.string.amount),
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Done
            )

            // 내용 입력
            CommonOutlinedTextField(
                value = uiState.content,
                onValueChange = { newContent ->
                    viewModel.setContent(newContent)
                },
                label = stringResource(R.string.content),
                trailingIcon = {
                    IconButton(
                        onClick = {
                            launcher.launch(
                                PickVisualMediaRequest(
                                    mediaType = ActivityResultContracts.PickVisualMedia.ImageOnly
                                )
                            )
                        }
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.ic_camera),
                            contentDescription = stringResource(R.string.add_image),
                            tint = Color(0xFF964BFF)
                        )
                    }
                }
            )

            // 선택한 이미지 미리보기
            ImagePreview(
                photoUri = uiState.photoUri,
                onRemove = { viewModel.clearPhotoUri() },
                modifier = modifier
                    .padding(top = 8.dp)
            )

            // 위치 입력
            CommonOutlinedTextField(
                value = uiState.location,
                onValueChange = {},
                label = stringResource(R.string.location),
                readOnly = true,
                trailingIcon = {
                    Icon(
                        imageVector = Icons.Outlined.LocationOn,
                        contentDescription = stringResource(R.string.location),
                        tint = Color(0xFF964BFF)
                    )
                },
            )

            Spacer(modifier = Modifier.height(8.dp))

            // 저장 버튼
            Button(
                onClick = {
                    // TODO: 저장
                },
                enabled = uiState.isSaveEnabled,
                modifier = modifier
                    .fillMaxWidth()
                    .height(48.dp),
                shape = RoundedCornerShape(4.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF964BFF)
                )
            ) {
                Text(
                    text = stringResource(R.string.save),
                    color = Color.White
                )
            }
        }
    }
}

@Preview
@Composable
fun Preview() {
    OurBudgetingTheme {
        TransactionAddScreen(TransactionType.EXPENSE)
    }
}