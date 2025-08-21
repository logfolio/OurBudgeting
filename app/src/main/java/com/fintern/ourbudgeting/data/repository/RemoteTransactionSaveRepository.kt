package com.fintern.ourbudgeting.data.repository

import android.net.Uri
import com.fintern.ourbudgeting.data.model.FirebaseConstants
import com.fintern.ourbudgeting.data.model.FirebaseConstants.FIELD_ASSET_TYPE
import com.fintern.ourbudgeting.ui.save.TransactionSaveUiState
import com.google.firebase.Timestamp
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.tasks.await
import java.util.Date
import java.util.UUID
import javax.inject.Inject

class RemoteTransactionSaveRepository @Inject constructor(
    private val firestore: FirebaseFirestore,
    private val storage: FirebaseStorage
) : TransactionSaveRepository {

    override suspend fun saveTransaction(
        householdId: String,
        uid: String,
        uiState: TransactionSaveUiState
    ): Result<Unit> {
        return runCatching {
            val photoUrl = uiState.photoUri?.let { uploadPhoto(it) }

            val transactionData = mapOf(
                FirebaseConstants.FIELD_TYPE to uiState.transactionType.name,
                FirebaseConstants.FIELD_DATE to uiState.selectedDate?.let { Timestamp(Date(it)) },
                FirebaseConstants.FIELD_ASSET_ID to uiState.selectedAsset,
                FirebaseConstants.FIELD_CATEGORY to uiState.selectedCategoryCode,
                FirebaseConstants.FIELD_AMOUNT to uiState.amount,
                FirebaseConstants.FIELD_DESCRIPTION to uiState.content,
                FirebaseConstants.FIELD_PHOTO_URL to photoUrl,
                FirebaseConstants.FIELD_CREATED_BY to uid,
                FirebaseConstants.FIELD_CREATED_AT to Timestamp.now()
            )

            firestore.collection(FirebaseConstants.COLLECTION_HOUSEHOLDS)
                .document(householdId)
                .collection(FirebaseConstants.COLLECTION_TRANSACTIONS)
                .add(transactionData)
                .await()

            Unit
        }
    }

    override suspend fun getAssetTypes(householdId: String): Result<List<String>> {
        return runCatching {
            val snapshot = firestore
                .collection(FirebaseConstants.COLLECTION_HOUSEHOLDS)
                .document(householdId)
                .get()
                .await()
                .get(FIELD_ASSET_TYPE)

            (snapshot as? List<*>)?.filterIsInstance<String>() ?: emptyList()
        }
    }

    private suspend fun uploadPhoto(uri: Uri): String {
        val fileName = "photos/${UUID.randomUUID()}.jpg"
        val ref = storage.reference.child(fileName)

        try {
            ref.putFile(uri).await()
            return ref.downloadUrl.await().toString()
        } catch (e: Exception) {
            throw e
        }
    }
}