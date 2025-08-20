package com.fintern.ourbudgeting.data.repository

import com.fintern.ourbudgeting.data.model.FirebaseConstants.COLLECTION_HOUSEHOLDS
import com.fintern.ourbudgeting.data.model.FirebaseConstants.COLLECTION_TRANSACTIONS
import com.fintern.ourbudgeting.data.model.FirebaseConstants.EXPENSE
import com.fintern.ourbudgeting.data.model.FirebaseConstants.FIELD_AMOUNT
import com.fintern.ourbudgeting.data.model.FirebaseConstants.FIELD_TYPE
import com.fintern.ourbudgeting.data.model.FirebaseConstants.INCOME
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Inject

class RemoteTotalAmountRepository @Inject constructor(
    private val firestore: FirebaseFirestore
) : TotalAmountRepository {

    override fun observeTotalAmount(householdId: String): Flow<Result<Long>> = callbackFlow {
        val ref =
            firestore.collection(COLLECTION_HOUSEHOLDS)
                .document(householdId)
                .collection(COLLECTION_TRANSACTIONS)

        val listener = ref.addSnapshotListener { snapshot, error ->
            if (error != null) {
                trySend(Result.failure(error))
                return@addSnapshotListener
            }

            val total = snapshot?.documents?.sumOf { doc ->
                val amount = doc.getLong(FIELD_AMOUNT) ?: 0L
                when (doc.getString(FIELD_TYPE)) {
                    INCOME -> amount
                    EXPENSE -> -amount
                    else -> 0L
                }
            } ?: 0L

            trySend(Result.success(total))
        }

        awaitClose { listener.remove() }
    }
}