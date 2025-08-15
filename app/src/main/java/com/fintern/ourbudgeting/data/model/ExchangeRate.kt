package com.fintern.ourbudgeting.data.model

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName

@Serializable
data class ExchangeRate(
    val result: Int,
    @SerialName("cur_unit")
    val curUnit: String,         // 통화코드
    @SerialName("cur_nm")
    val curNm: String,           // 국가/통화명
    @SerialName("deal_bas_r")
    val dealBasR: String
)