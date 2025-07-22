package com.fintern.ourbudgeting.data.repository

import com.google.firebase.firestore.FirebaseFirestore

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

        return mapOf();
    }
}
