package com.fintern.ourbudgeting.ui.assetmanagement

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import javax.inject.Inject


class AddAssetRepository @Inject constructor(
    private val db: FirebaseFirestore,
    private val auth: FirebaseAuth
) {
    suspend fun initializeUserHousehold(): Result<Unit> {
        val user = auth.currentUser ?: return Result.failure(Exception("로그인 안됨"))
        val householdRef = db.collection("users").document(user.uid)
        return try {
            val snapshot = householdRef.get().await()
            if (!snapshot.exists()) {


                val initialData = mapOf(
                    "assetType" to listOf("현금"),
                    "createdAt" to System.currentTimeMillis(),
                    "userId" to user.uid
                )
                householdRef.set(initialData).await()
            }
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }


    suspend fun addAssetType(assetType: String): Result<Unit> {
        val user = auth.currentUser ?: return Result.failure(Exception("로그인 안됨"))
        val householdRef = db.collection("users").document(user.uid)


        return try {
            // 먼저 households 문서 초기화 확인
            initializeUserHousehold()


            // assetType 필드에 새로운 값 추가
            householdRef.update("assetType", FieldValue.arrayUnion(assetType)).await()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }




}
