package com.fintern.ourbudgeting.data.repository

import com.fintern.ourbudgeting.data.calendar.Transaction
import com.fintern.ourbudgeting.data.calendar.TransactionWithId
import com.fintern.ourbudgeting.ui.calendar.UiState
import com.fintern.ourbudgeting.ui.calendar.component.FilterType
import com.fintern.ourbudgeting.ui.common.model.TransactionType
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.catch
import javax.inject.Inject

class TransactionRepositoryImpl @Inject constructor(
    private val firestore: FirebaseFirestore
) : TransactionRepository {
    override fun getTransactions(
        householdId: String,
        filter: FilterType
    ): Flow<UiState<List<TransactionWithId>>> = callbackFlow {
        trySend(UiState.Loading)

        val collectionRef = firestore
            .collection("households")
            .document(householdId)
            .collection("transactions")

        val listener = collectionRef.addSnapshotListener { snapshot, error ->
            if (error != null) {
                trySend(UiState.Error(error))
                return@addSnapshotListener
            }

            if (snapshot != null) {
                val allItems = snapshot.documents.mapNotNull { doc ->
                    doc.toObject(Transaction::class.java)?.let {
                        TransactionWithId(doc.id, it)
                    }
                }

                val filtered = when (filter) {
                    FilterType.ALL -> allItems
                    FilterType.INCOME -> allItems.filter { it.transaction.type == TransactionType.INCOME }
                    FilterType.EXPENSE -> allItems.filter { it.transaction.type == TransactionType.EXPENSE }
                }

                trySend(UiState.Success(filtered))
            }
        }

        awaitClose { listener.remove() }
    }.catch {
        emit(UiState.Error(it))
    }
}