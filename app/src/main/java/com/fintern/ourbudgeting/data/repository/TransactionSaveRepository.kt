package com.fintern.ourbudgeting.data.repository

import android.net.Uri
import com.fintern.ourbudgeting.ui.save.TransactionAddUiState
import com.google.firebase.Timestamp
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.tasks.await
import java.util.Date
import java.util.UUID
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TransactionRepository @Inject constructor(
    private val firestore: FirebaseFirestore,
    private val storage: FirebaseStorage
) {

    suspend fun saveTransaction(
        householdId: String,
        uid: String,
        uiState: TransactionAddUiState
    ): Result<Unit> {
        return runCatching {
            val photoUrl = uiState.photoUri?.let { uploadPhoto(it) }

            val transactionData = mapOf(
                "type" to uiState.transactionType.name,
                "date" to uiState.selectedDate?.let { Timestamp(Date(it)) },
                "assetId" to uiState.selectedAsset,
                "category" to uiState.selectedCategory,
                "amount" to uiState.amount,
                "description" to uiState.content,
                "photoUrl" to photoUrl,
                "createdBy" to uid,
                "createdAt" to Timestamp.now()
            )

            firestore.collection("households")
                .document(householdId)
                .collection("transactions")
                .add(transactionData)
                .await()

            Unit
        }
    }

    private suspend fun uploadPhoto(uri: Uri): String {
        val fileName = "photos/${UUID.randomUUID()}.jpg"
        val ref = storage.reference.child(fileName)

        ref.putFile(uri).await()
        return ref.downloadUrl.await().toString()
    }
}