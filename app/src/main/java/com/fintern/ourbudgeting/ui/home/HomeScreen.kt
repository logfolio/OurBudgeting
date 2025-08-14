package com.fintern.ourbudgeting.ui.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.fintern.ourbudgeting.R
import com.fintern.ourbudgeting.ui.home.components.AssetSummaryCard
import com.fintern.ourbudgeting.ui.home.components.ExchangeRateCard
import com.fintern.ourbudgeting.ui.home.components.LatestTransactionCard
import com.fintern.ourbudgeting.ui.user.UserViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    onAddIncomeClick: () -> Unit,
    onAddExpenseClick: () -> Unit,
    homeViewModel: HomeViewModel = hiltViewModel(),
    viewModel: UserViewModel = hiltViewModel(),
) {
    val uiState by homeViewModel.uiState.collectAsState()

    val uid by viewModel.uid.collectAsState()
    val nickname by viewModel.nickname.collectAsState()
    val household by viewModel.household.collectAsState()
    val isHouseholdLoading by viewModel.isHouseholdLoading.collectAsState()

    // 사용자가 화면에 들어왔을 때 household 초기화
    LaunchedEffect(uid) {
        if (uid.isNotEmpty()) {
            viewModel.initializeUserHousehold()
        }
    }

    Column(
        modifier = Modifier.padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            stringResource(R.string.calendar_label_app_name),
            style = MaterialTheme.typography.titleLarge
        )

        AssetSummaryCard(
            amount = uiState.totalAssetText,
            onAddIncomeClick = onAddIncomeClick,
            onAddExpenseClick = onAddExpenseClick,
        )

        Text(
            text = stringResource(R.string.latest_transaction_content),
            fontWeight = FontWeight.SemiBold,
            style = MaterialTheme.typography.titleMedium,
        )

        uiState.latestTransaction.forEach { transaction ->
            LatestTransactionCard(
                content = transaction.content,
                amount = transaction.amountText,
                imageUri = transaction.imageUri
            )
        }

        Spacer(modifier = Modifier.padding(vertical = 8.dp))

        Text(
            text = stringResource(R.string.exchange_rate),
            fontWeight = FontWeight.SemiBold,
            style = MaterialTheme.typography.titleMedium,
        )

        uiState.exchangeRates.forEach { r ->
            ExchangeRateCard(
                countryName = r.countryName,
                currencyCode = r.currencyCode,
                exchangeRate = r.rateText,
            )
        }
    }
}