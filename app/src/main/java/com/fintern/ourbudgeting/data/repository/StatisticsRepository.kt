package com.fintern.ourbudgeting.data.repository

import com.google.firebase.Timestamp
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
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

        val query = firestore.collection("households")
            .document(householdId)
            .collection("transactions")
            .whereEqualTo("type", type)
            .whereEqualTo("createdBy", uid)
            .whereGreaterThanOrEqualTo("date", startTimestamp)
            .whereLessThan("date", endTimestamp)

        val snapshot = query.get().await()

        return snapshot.documents
            .mapNotNull { it.data }
            .groupBy { it["category"] as? String ?: "Uncategorized" }
            .mapValues { (_, docs) ->
                docs.sumOf { (it["amount"] as? Number)?.toDouble() ?: 0.0 }
            }
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