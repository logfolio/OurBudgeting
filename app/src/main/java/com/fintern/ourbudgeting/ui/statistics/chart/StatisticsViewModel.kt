package com.fintern.ourbudgeting.ui.statistics.chart

import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fintern.ourbudgeting.data.chart.PieEntry
import com.fintern.ourbudgeting.data.repository.StatisticsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StatisticsViewModel @Inject constructor(
    private val repository: StatisticsRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(StatisticsUiState())
    val uiState: StateFlow<StatisticsUiState> = _uiState.asStateFlow()

    fun updateMonthAndFetchData(
        offset: Int,
        uid: String,
        householdId: String
    ) {
        val currentMonth = _uiState.value.currentMonth
        val currentYear = _uiState.value.currentYear

        var newMonth = currentMonth + offset
        var newYear = currentYear

        if (newMonth > 12) {
            newMonth = 1
            newYear += 1
        } else if (newMonth < 1) {
            newMonth = 12
            newYear -= 1
        }

        fetchMonthlyCategoryTotals(
            year = newYear,
            month = newMonth,
            uid = uid,
            householdId = householdId
        )
    }

    fun fetchMonthlyCategoryTotals(
        year: Int,
        month: Int,
        uid: String,
        householdId: String
    ) {
        _uiState.value = _uiState.value.copy(
            isLoading = true,
            errorMessage = null,
            currentYear = year,
            currentMonth = month
        )

        viewModelScope.launch {
            try {
                val categorySums = repository.fetchMonthlyCategoryTotals(
                    // TODO : type 변경
                    type = "expense",
                    year = year,
                    month = month,
                    uid = uid,
                    householdId = householdId
                )

                val entries = categorySums.map { (category, amount) ->
                    PieEntry(
                        amount.toFloat(),
                        category,
                        pieColor = getColorForCategory(category)
                    )
                }

                _uiState.value = _uiState.value.copy(
                    chartData = entries,
                    isLoading = false,
                    errorMessage = null
                )
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    errorMessage = e.message ?: "알 수 없는 오류가 발생했습니다.",
                    isLoading = false
                )
            }
        }
    }

    private fun getColorForCategory(category: String) = when (category) {
        "식비" -> Color(0xFFE57373)
        "교통" -> Color(0xFF64B5F6)
        "의료" -> Color(0xFF81C784)
        "쇼핑" -> Color(0xFFFFB74D)
        "기타" -> Color(0xFFBA68C8)
        else -> Color.Gray
    }
}