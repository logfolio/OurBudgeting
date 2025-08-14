package com.fintern.ourbudgeting.data.repository

import com.fintern.ourbudgeting.data.model.FirebaseConstants
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

        val query = firestore.collection(FirebaseConstants.COLLECTION_HOUSEHOLDS)
            .document(householdId)
            .collection(FirebaseConstants.COLLECTION_TRANSACTIONS)
            .whereEqualTo(FirebaseConstants.FIELD_TYPE, type)
            .whereEqualTo(FirebaseConstants.FIELD_CREATED_BY, uid)
            .whereGreaterThanOrEqualTo(FirebaseConstants.FIELD_DATE, startTimestamp)
            .whereLessThan(FirebaseConstants.FIELD_DATE, endTimestamp)

        val snapshot = query.get().await()

        return snapshot.documents
            .mapNotNull { it.data }
            .groupBy { it[FirebaseConstants.FIELD_CATEGORY] as? String ?: "Uncategorized" }
            .mapValues { (_, docs) ->
                docs.sumOf { (it[FirebaseConstants.FIELD_AMOUNT] as? Number)?.toDouble() ?: 0.0 }
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