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
            } catch (e: Exception) {

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