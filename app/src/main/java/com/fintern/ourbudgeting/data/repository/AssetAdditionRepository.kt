package com.fintern.ourbudgeting.data.repository

import com.fintern.ourbudgeting.data.model.FirebaseConstants
import com.fintern.ourbudgeting.ui.assetmanagement.assettypeaddition.AssetRepositoryException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import javax.inject.Inject


class AssetAdditionRepository @Inject constructor(
    private val db: FirebaseFirestore,
    private val auth: FirebaseAuth
) {
    suspend fun initializeUserHousehold(
        defaultAssetType: String,
        householdsId: String
    ): Result<Unit> {
        val user =
            auth.currentUser ?: return Result.failure(AssetRepositoryException.UserNotAuthenticated)
        val householdRef = db.collection(FirebaseConstants.COLLECTION_USERS).document(householdsId)
        return runCatching {
            val snapshot = householdRef.get().await()
            if (!snapshot.exists()) {
                val initialData = mapOf(
                    FirebaseConstants.FIELD_ASSET_TYPE to listOf(defaultAssetType),
                    FirebaseConstants.FIELD_CREATED_AT to System.currentTimeMillis(),
                    FirebaseConstants.FIELD_USER_ID to user.uid
                )
                householdRef.set(initialData).await()
            } else {
                val data = snapshot.data
                if (data?.containsKey(FirebaseConstants.FIELD_ASSET_TYPE) != true) {
                    householdRef.update(FirebaseConstants.FIELD_ASSET_TYPE, listOf(defaultAssetType)).await()
                }
            }
        }
    }

    suspend fun addAssetType(assetType: String, householdId: String): Result<Unit> {
        val user =
            auth.currentUser ?: return Result.failure(AssetRepositoryException.UserNotAuthenticated)
        val householdRef = db.collection(FirebaseConstants.COLLECTION_HOUSEHOLDS).document(householdId)
        return runCatching {
            householdRef.update(FirebaseConstants.FIELD_ASSET_TYPE, FieldValue.arrayUnion(assetType)).await()
        }
    }
}
