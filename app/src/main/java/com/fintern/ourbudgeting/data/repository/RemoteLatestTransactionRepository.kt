package com.fintern.ourbudgeting.data.repository

import com.fintern.ourbudgeting.data.calendar.Transaction
import com.fintern.ourbudgeting.data.calendar.TransactionWithId
import com.fintern.ourbudgeting.data.model.FirebaseConstants
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Inject

class RemoteLatestTransactionRepository @Inject constructor(
    private val firestore: FirebaseFirestore
) : LatestTransactionRepository {

    override fun getLatestTransactions(
        householdId: String,
    ): Flow<List<TransactionWithId>> = callbackFlow {
        val collectionRef = firestore
            .collection(FirebaseConstants.COLLECTION_HOUSEHOLDS)
            .document(householdId)
            .collection(FirebaseConstants.COLLECTION_TRANSACTIONS)
            .orderBy(FirebaseConstants.FIELD_DATE, Query.Direction.DESCENDING)
            .limit(5)

        val listener = collectionRef.addSnapshotListener { snapshot, error ->
            if (error != null) {
                close(error)
                return@addSnapshotListener
            }

            val list = snapshot?.documents.orEmpty().mapNotNull { doc ->
                runCatching { doc.toObject(Transaction::class.java) }
                    .getOrNull()
                    ?.let { transaction -> TransactionWithId(doc.id, transaction) }
            }

            trySend(list)
        }

        awaitClose { listener.remove() }
    }
}