package com.fintern.ourbudgeting.ui.assetmanagement.assetdisplay

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.catch
import javax.inject.Inject

class AssetDisplayRepository @Inject constructor(
    private val auth: FirebaseAuth,
    private val db: FirebaseFirestore
) {
    fun getAssetSummary(householdId: String): Flow<Result<AssetSummary>> = callbackFlow {
        val transactionsRef = db
            .collection("households")
            .document(householdId)
            .collection("transactions")

        val listener = transactionsRef.addSnapshotListener { snapshot, error ->
            if (error != null) {
                trySend(Result.failure(error))
                return@addSnapshotListener
            }

            if (snapshot != null) {
                try {
                    val transactions = snapshot.documents.mapNotNull { doc ->
                        doc.toObject(AssetTransaction::class.java)?.copy(id = doc.id)
                    }
                    val totalAsset = transactions
                        .filter { it.type == "INCOME" }
                        .sumOf { it.amount }

                    val totalDebt = transactions
                        .filter { it.type == "EXPENSE" }
                        .sumOf { it.amount }

                    val assetDetails = transactions
                        .groupBy { it.assetId }
                        .map { (assetName, assetTransactions) ->
                            val incomeAmount = assetTransactions
                                .filter { it.type == "INCOME" }
                                .sumOf { it.amount }

                            val expenseAmount = assetTransactions
                                .filter { it.type == "EXPENSE" }
                                .sumOf { it.amount }

                            val totalAmount = incomeAmount - expenseAmount

                            AssetDetail(
                                assetName = assetName,
                                totalAmount = totalAmount,
                                incomeAmount = incomeAmount,
                                expenseAmount = expenseAmount
                            )
                        }
                        .sortedByDescending { it.totalAmount }
                    val summary = AssetSummary(
                        totalAsset = totalAsset,
                        totalDebt = totalDebt,
                        netWorth = totalAsset - totalDebt,
                        assetDetails = assetDetails
                    )
                    trySend(Result.success(summary))
                } catch (e: Exception) {
                    trySend(Result.failure(e))
                }
            }
        }
        awaitClose { listener.remove() }
    }.catch {
        emit(Result.failure(it))
    }
}