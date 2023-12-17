package com.example.pilleat.taker.response

import com.google.gson.annotations.SerializedName

data class EnrollPillListResponse(
    @SerializedName("isSuccess") val isSuccess: Boolean,
    @SerializedName("code") val code: Int,
    @SerializedName("message") val message: String,
    @SerializedName("result") val result: Drugs
)

data class Drugs(
    @SerializedName("drugs") val drugs: ArrayList<PillList>
)

data class PillList(
    @SerializedName("drugId") val drugId: Int,
    @SerializedName("name") val name: String,
    @SerializedName("category") val category: String,
    @SerializedName("time") val time: String,
    @SerializedName("day") val day: Array<String>,
    @SerializedName("iot") val iot: Int
)
