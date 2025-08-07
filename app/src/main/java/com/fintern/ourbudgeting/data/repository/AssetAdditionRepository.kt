package com.fintern.ourbudgeting.data.repository

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
    suspend fun initializeUserHousehold(): Result<Unit> {
        val user =
            auth.currentUser ?: return Result.failure(AssetRepositoryException.UserNotAuthenticated)
        val householdRef = db.collection("users").document(user.uid)
        return runCatching {
            val snapshot = householdRef.get().await()
            if (!snapshot.exists()) {
                val initialData = mapOf(
                    "assetType" to listOf("현금"),
                    "createdAt" to System.currentTimeMillis(),
                    "userId" to user.uid
                )
                householdRef.set(initialData).await()
            }
        }
    }

    suspend fun addAssetType(assetType: String): Result<Unit> {
        val user =
            auth.currentUser ?: return Result.failure(AssetRepositoryException.UserNotAuthenticated)
        val householdRef = db.collection("users").document(user.uid)
        return runCatching {
            initializeUserHousehold().getOrThrow() //Result<Unit>을 반환해서 exception 발생하지 않음. 그래서 getOrThrow() 사용
            householdRef.update("assetType", FieldValue.arrayUnion(assetType)).await()
        }
    }
}
