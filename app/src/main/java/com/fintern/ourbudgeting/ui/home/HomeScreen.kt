package com.fintern.ourbudgeting.ui.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
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
import com.fintern.ourbudgeting.util.NumberUtils.formatAmount

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    householdId: String,
    onAddIncomeClick: () -> Unit,
    onAddExpenseClick: () -> Unit,
    homeViewModel: HomeViewModel = hiltViewModel(),
    viewModel: UserViewModel = hiltViewModel(),
) {
    val uiState by homeViewModel.uiState.collectAsState()

    val uid by viewModel.uid.collectAsState()

    LaunchedEffect(uid) {
        if (uid.isNotEmpty() && viewModel.household.value == null) {
            viewModel.initializeUserHousehold()
        }
    }

    LaunchedEffect(householdId) {
        if (householdId.isNotEmpty()) {
            homeViewModel.getLatestTransactions(householdId)
            homeViewModel.observeTotalAmount(householdId)
        }
    }

    LaunchedEffect(Unit) {
        homeViewModel.loadExchangeRates()
        homeViewModel.observeTotalAmount(householdId)
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
            amount = formatAmount(uiState.totalAssetText),
            onAddIncomeClick = onAddIncomeClick,
            onAddExpenseClick = onAddExpenseClick,
        )

        Text(
            text = stringResource(R.string.latest_transaction_content),
            fontWeight = FontWeight.SemiBold,
            style = MaterialTheme.typography.titleMedium,
        )

        LazyRow(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(uiState.latestTransaction) { transaction ->
                LatestTransactionCard(
                    content = transaction.content,
                    amount = transaction.amountText,
                    imageUri = transaction.imageUri,
                )
            }
        }

        Spacer(modifier = Modifier.padding(vertical = 8.dp))

        Text(
            text = stringResource(R.string.exchange_rate),
            fontWeight = FontWeight.SemiBold,
            style = MaterialTheme.typography.titleMedium,
        )

        when {
            uiState.isLoading -> {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    CircularProgressIndicator()
                }
            }

            uiState.error != null -> {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                ) {
                    Text(
                        text = stringResource(uiState.error!!.messageResId),
                        color = MaterialTheme.colorScheme.error,
                        style = MaterialTheme.typography.bodyMedium
                    )
                    Spacer(modifier = Modifier.padding(4.dp))
                }
            }

            uiState.exchangeRates.isEmpty() -> {
                Text(
                    text = stringResource(R.string.error_exchange_no_data),
                    style = MaterialTheme.typography.bodyMedium
                )
            }

            else -> {
                uiState.exchangeRates.forEach { exchangeRates ->
                    ExchangeRateCard(
                        countryName = exchangeRates.countryName,
                        currencyCode = exchangeRates.currencyCode,
                        exchangeRate = exchangeRates.rateText,
                    )
                }
            }
        }
    }
}