package com.fintern.ourbudgeting.ui.statistics.chart

import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fintern.ourbudgeting.R
import com.fintern.ourbudgeting.data.chart.PieEntry
import com.fintern.ourbudgeting.data.model.ExpenseCategoryType
import com.fintern.ourbudgeting.data.model.IncomeCategoryType
import com.fintern.ourbudgeting.data.repository.StatisticsRepository
import com.fintern.ourbudgeting.ui.common.model.FirebaseError
import com.fintern.ourbudgeting.ui.common.model.TransactionType
import com.google.firebase.FirebaseNetworkException
import com.google.firebase.firestore.FirebaseFirestoreException
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.time.Clock
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class StatisticsViewModel @Inject constructor(
    private val repository: StatisticsRepository,
    private val clock: Clock,
) : ViewModel() {
    private val now = LocalDate.now(clock)

    private val _uiState = MutableStateFlow(
        StatisticsUiState(
            currentYear = now.year,
            currentMonth = now.monthValue
        )
    )
    val uiState: StateFlow<StatisticsUiState> = _uiState.asStateFlow()

    fun initialize(
        uid: String,
        householdId: String,
    ) {
        val current = _uiState.value
        fetchMonthlyCategoryTotals(
            year = current.currentYear,
            month = current.currentMonth,
            uid = uid,
            householdId = householdId,
            type = current.currentType
        )
    }

    fun updateMonthAndFetchData(
        offset: Int,
        uid: String,
        householdId: String
    ) {
        val current = _uiState.value
        val (newYear, newMonth) = calculateNewYearMonth(
            current.currentYear,
            current.currentMonth,
            offset
        )

        fetchMonthlyCategoryTotals(
            year = newYear,
            month = newMonth,
            uid = uid,
            householdId = householdId,
            type = current.currentType,
        )
    }

    fun updateTypeAndFetchData(
        type: TransactionType,
        uid: String,
        householdId: String,
    ) {
        val current = _uiState.value.copy(currentType = type)

        fetchMonthlyCategoryTotals(
            year = current.currentYear,
            month = current.currentMonth,
            uid = uid,
            householdId = householdId,
            type = type,
        )
    }

    private fun fetchMonthlyCategoryTotals(
        year: Int,
        month: Int,
        uid: String,
        householdId: String,
        type: TransactionType,
    ) {
        _uiState.value = _uiState.value.copy(
            isLoading = true,
            error = null,
            currentYear = year,
            currentMonth = month,
            currentType = type,
        )

        viewModelScope.launch {
            try {
                val categorySums = repository.fetchMonthlyCategoryTotals(
                    type = type.name.lowercase(),
                    year = year,
                    month = month,
                    uid = uid,
                    householdId = householdId
                )

                val entries = categorySums.map { (category, amount) ->
                    PieEntry(
                        value = amount.toFloat(),
                        pieLabel = category,
                        pieColor = getColorForCategory(type, category),
                        labelResId = getLabelResIdForCategory(type, category)
                    )
                }

                _uiState.value = _uiState.value.copy(
                    chartData = entries,
                    error = null
                )
            } catch (e: FirebaseNetworkException) {
                _uiState.value = _uiState.value.copy(
                    error = FirebaseError.NetworkError
                )
            } catch (e: FirebaseFirestoreException) {
                _uiState.value = _uiState.value.copy(
                    error = FirebaseError.FirestoreError
                )
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    error = FirebaseError.UnknownError
                )
            } finally {
                _uiState.value = _uiState.value.copy(
                    isLoading = false
                )
            }
        }
    }

    private fun calculateNewYearMonth(
        currentYear: Int,
        currentMonth: Int,
        offset: Int
    ): Pair<Int, Int> {
        val totalMonths = currentYear * 12 + (currentMonth - 1) + offset
        val newYear = totalMonths / 12
        val newMonth = (totalMonths % 12) + 1
        return Pair(newYear, newMonth)
    }

    private fun getColorForCategory(
        type: TransactionType,
        category: String,
    ): Color = when (type) {
        TransactionType.EXPENSE -> {
            ExpenseCategoryType.entries.find { it.name == category }?.color ?: Color.Gray
        }

        TransactionType.INCOME -> {
            IncomeCategoryType.entries.find { it.name == category }?.color ?: Color.Gray
        }
    }

    private fun getLabelResIdForCategory(
        type: TransactionType,
        category: String
    ): Int = when (type) {
        TransactionType.EXPENSE -> {
            ExpenseCategoryType.entries.find { it.name == category }?.labelRes
                ?: R.string.expense_etc
        }

        TransactionType.INCOME -> {
            IncomeCategoryType.entries.find { it.name == category }?.labelRes ?: R.string.income_etc
        }
    }
}