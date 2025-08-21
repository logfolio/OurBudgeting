package com.fintern.ourbudgeting.ui.save

import android.content.Context
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
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
import com.fintern.ourbudgeting.ui.save.componenet.TransactionTopAppBar
import com.fintern.ourbudgeting.ui.user.UserViewModel
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.text.TextRecognition
import com.google.mlkit.vision.text.korean.KoreanTextRecognizerOptions

@Composable
fun TransactionSaveScreen(
    initialTransactionType: TransactionType,
    householdId: String,
    onNavigateToBack: () -> Unit,
    modifier: Modifier = Modifier,
    transactionViewModel: TransactionSaveViewModel = hiltViewModel(),
    userViewModel: UserViewModel = hiltViewModel(),
) {
    val context = LocalContext.current
    val uiState by transactionViewModel.uiState.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }

    val uid by userViewModel.uid.collectAsState()

    // 이미지 업데이트용
    val imageOnlyLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
            uri?.let {
                transactionViewModel.setPhotoUri(it)
            }
        }

    // OCR 수행용
    val ocrLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
            uri?.let {
                processReceiptImage(context, it) { result ->
                    transactionViewModel.applyScannedReceipt(result)
                }
            }
        }

    LaunchedEffect(Unit) {
        transactionViewModel.eventFlow.collect { event ->
            when (event) {
                is TransactionUiEvent.ShowSnackbar -> {
                    snackbarHostState.showSnackbar(
                        message = context.getString(event.messageResId)
                    )
                }

                TransactionUiEvent.Success -> {
                    snackbarHostState.showSnackbar(
                        message = context.getString(R.string.save_success)
                    )
                }
            }
        }
    }

    LaunchedEffect(initialTransactionType) {
        transactionViewModel.setTransactionType(initialTransactionType)
    }

    Scaffold(
        topBar = {
            TransactionTopAppBar(
                uiState = uiState,
                onNavigateToBack = onNavigateToBack,
                onCameraClick = {
                    ocrLauncher.launch(
                        PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                    )
                }
            )
        },
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
        },
    ) { innerPadding ->
        val (categoryEnums, categoryLabels) = when (uiState.transactionType) {
            TransactionType.EXPENSE -> {
                val enums = ExpenseCategoryType.entries
                enums to enums.map { stringResource(id = it.labelRes) }
            }

            TransactionType.INCOME -> {
                val enums = IncomeCategoryType.entries
                enums to enums.map { stringResource(id = it.labelRes) }
            }
        }

        if (uiState.isLoading) {
            Box(
                modifier = Modifier
                    .fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(
                    color = Color(0xFF964BFF)
                )
            }
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
                    transactionViewModel.setTransactionType(it)
                }
            )

            DatePickerField(
                label = stringResource(R.string.select_date_label),
                onDateSelected = { transactionDate ->
                    transactionViewModel.setSelectedDate(transactionDate)
                },
                modifier = modifier
            )

            // 자산 선택
            DropDownField(
                label = stringResource(R.string.asset),
                options = listOf("은행 계좌", "현금", "카드"),
                // TODO: Firestore에서 데이터 불러와서 표시
                onOptionSelected = { selected ->
                    transactionViewModel.setSelectedAsset(selected)
                },
            )

            // 카테고리 선택
            DropDownField(
                label = stringResource(R.string.category),
                options = categoryLabels,
                onOptionSelected = { selectedLabel ->
                    val label = categoryLabels.indexOf(selectedLabel)
                    val code = categoryEnums[label].name
                    transactionViewModel.setCategory(selectedLabel, code)
                },
            )

            // 금액 입력
            CommonOutlinedTextField(
                value = uiState.amountTextFieldValue,
                onValueChange = { newValue ->
                    transactionViewModel.setAmountTextFieldValue(newValue)
                },
                label = stringResource(R.string.amount),
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Done
            )

            // 내용 입력
            CommonOutlinedTextField(
                value = uiState.content,
                onValueChange = { newContent ->
                    transactionViewModel.setContent(newContent)
                },
                label = stringResource(R.string.content),
                trailingIcon = {
                    IconButton(
                        onClick = {
                            imageOnlyLauncher.launch(
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
                onRemove = { transactionViewModel.clearPhotoUri() },
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
                    transactionViewModel.saveTransaction(householdId, uid)
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

private fun processReceiptImage(context: Context, imageUri: Uri, onSuccess: (String) -> Unit) {
    val recognizer = TextRecognition.getClient(KoreanTextRecognizerOptions.Builder().build())
    val image = InputImage.fromFilePath(context, imageUri)

    recognizer.process(image)
        .addOnSuccessListener { result ->
            onSuccess(result.text)
        }
}