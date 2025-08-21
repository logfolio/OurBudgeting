package com.fintern.ourbudgeting.data.repository

import com.fintern.ourbudgeting.data.model.FirebaseConstants
import com.fintern.ourbudgeting.ui.common.model.Household
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class HouseholdRepository @Inject constructor(
    private val firestore: FirebaseFirestore
) {
    private val householdsCollection = firestore.collection(FirebaseConstants.COLLECTION_HOUSEHOLDS)

    suspend fun createInitialHousehold(userId: String): Result<String> {
        return try {
            val household = Household(
                assetType = listOf("현금"),
                currency = "KRW",
                isPrivate = true,
                name = "개인 가계부",
                ownerId = userId
            )
            val documentRef = householdsCollection.add(household).await()
            Result.success(documentRef.id)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun getUserHousehold(userId: String): Result<Household?> {
        return try {
            val querySnapshot = householdsCollection
                .whereEqualTo(FirebaseConstants.FIELD_OWNER_ID, userId)
                .limit(1)
                .get()
                .await()

            if (querySnapshot.isEmpty) {
                Result.success(null)
            } else {
                val document = querySnapshot.documents.first()
                val household = document.toObject(Household::class.java)?.copy(id = document.id)
                Result.success(household)
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun updateHouseholdName(ownerId: String, newName: String): Result<String> {
        return try {
            val householdRef = householdsCollection.document(ownerId)
            householdRef.update(FirebaseConstants.FIELD_NAME, newName).await()
            Result.success(newName)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}