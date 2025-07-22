package com.fintern.ourbudgeting.data.repository

import com.google.firebase.Timestamp
import com.google.firebase.firestore.FirebaseFirestore
import java.util.Calendar

class StatisticsRepository(
    private val firestore: FirebaseFirestore
) {
    suspend fun fetchMonthlyCategoryTotals(
        type: String,
        year: Int,
        month: Int,
        uid: String,
        householdId: String
    ): Map<String, Double> {
        val startTimestamp = getStartTimestamp(year, month)
        val endTimestamp = getEndTimestamp(year, month)

        return mapOf()
    }

    private fun getStartTimestamp(year: Int, month: Int): Timestamp {
        val cal = Calendar.getInstance().apply {
            clear()
            set(Calendar.YEAR, year)
            set(Calendar.MONTH, month - 1)
            set(Calendar.DAY_OF_MONTH, 1)
        }
        return Timestamp(cal.time)
    }

    private fun getEndTimestamp(year: Int, month: Int): Timestamp {
        val cal = Calendar.getInstance().apply {
            clear()
            set(Calendar.YEAR, year)
            set(Calendar.MONTH, month)
            set(Calendar.DAY_OF_MONTH, 1)
        }
        return Timestamp(cal.time)
    }
}
