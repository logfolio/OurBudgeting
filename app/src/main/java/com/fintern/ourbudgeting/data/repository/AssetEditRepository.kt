package com.fintern.ourbudgeting.data.repository

import com.fintern.ourbudgeting.data.model.FirebaseConstants
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject


class AssetEditRepository @Inject constructor(
    private val db : FirebaseFirestore
) {
    fun getAssetTypesFlow(householdId: String): Flow<List<String>> {
        return callbackFlow {
            val listener = db.collection(FirebaseConstants.COLLECTION_HOUSEHOLDS)
                .document(householdId)
                .addSnapshotListener { snapshot, error ->
                    if (error != null) {
                        close(error)
                        return@addSnapshotListener
                    }

                    if (snapshot != null && snapshot.exists()) {
                        val assetTypes =
                            (snapshot.get(FirebaseConstants.FIELD_ASSET_TYPE) as? List<*>)?.filterIsInstance<String>()
                                ?: emptyList()

                        trySend(assetTypes)
                    } else {
                        trySend(emptyList())
                    }
                }
            awaitClose { listener.remove() }
        }
    }

    suspend fun updateAssetType(householdId: String, oldAssetType: String, newAssetType: String): Result<Unit> {
        return runCatching {
            val documentRef = db.collection(FirebaseConstants.COLLECTION_HOUSEHOLDS).document(householdId)
            val transactionsCollectionRef = db.collection(FirebaseConstants.COLLECTION_HOUSEHOLDS)
                .document(householdId)
                .collection(FirebaseConstants.COLLECTION_TRANSACTIONS)

            val transactionsWithOldAssetType = transactionsCollectionRef
                .get()
                .await()

            db.runTransaction { transaction ->
                val snapshot = transaction.get(documentRef)
                val currentAssetTypes =
                    (snapshot.get(FirebaseConstants.FIELD_ASSET_TYPE) as? List<*>)?.filterIsInstance<String>()
                        ?: emptyList()
                if (currentAssetTypes.contains(newAssetType) && oldAssetType != newAssetType) {
                    throw Exception()
                }

                val updatedAssetTypes = currentAssetTypes.map {
                    if (it == oldAssetType) newAssetType else it
                }

                transaction.update(documentRef, FirebaseConstants.FIELD_ASSET_TYPE, updatedAssetTypes)

                transactionsWithOldAssetType.documents.forEach { transactionDoc ->
                    val data = transactionDoc.data ?: return@forEach
                    var needsUpdate = false
                    val updatedData = mutableMapOf<String, Any>()
                    data.forEach { (key, value) ->
                        if (key == FirebaseConstants.FIELD_ASSET_ID && value == oldAssetType) {
                            updatedData[key] = newAssetType
                            needsUpdate = true
                        }
                    }
                    if (needsUpdate) {
                        transaction.update(transactionDoc.reference, updatedData)
                    }
                }
            }.await()
        }
    }
}