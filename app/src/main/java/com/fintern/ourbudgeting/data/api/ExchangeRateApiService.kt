package com.fintern.ourbudgeting.data.api

import com.fintern.ourbudgeting.data.model.ExchangeRate
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ExchangeRateApiService {
    @GET("site/program/financial/exchangeJSON")
    suspend fun getExchangeRates(
        @Query("authkey") authKey: String,
        @Query("searchdate") searchDate: String? = null,
        @Query("data") data: String = "AP01"
    ): Response<List<ExchangeRate>>
}