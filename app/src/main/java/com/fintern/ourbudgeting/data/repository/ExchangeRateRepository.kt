package com.fintern.ourbudgeting.data.repository

import com.fintern.ourbudgeting.data.model.ExchangeRate
import com.fintern.ourbudgeting.data.api.ExchangeRateApiService
import javax.inject.Inject

class ExchangeRateRepository @Inject constructor(
    private val api: ExchangeRateApiService
) {
    suspend fun getExchangeRates(
        authKey: String,
        searchDate: String? = null
    ): Result<List<ExchangeRate>> {
        return try {
            val response = api.getExchangeRates(authKey, searchDate)
            if (response.isSuccessful) {
                Result.success(response.body() ?: emptyList())
            } else {
                Result.failure(Exception("${response.code()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}