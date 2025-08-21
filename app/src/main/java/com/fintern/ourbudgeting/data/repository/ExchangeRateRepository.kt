package com.fintern.ourbudgeting.data.repository

import com.fintern.ourbudgeting.BuildConfig
import com.fintern.ourbudgeting.data.api.ExchangeRateApiService
import com.fintern.ourbudgeting.data.model.ExchangeRate
import javax.inject.Inject

class ExchangeRateRepository @Inject constructor(
    private val api: ExchangeRateApiService
) {
    suspend fun getExchangeRates(
        searchDate: String? = null
    ): Result<List<ExchangeRate>> {
        return try {
            val authKey = BuildConfig.EXCHANGE_RATE_API_KEY
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