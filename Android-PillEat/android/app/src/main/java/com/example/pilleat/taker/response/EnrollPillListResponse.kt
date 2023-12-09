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
    @SerializedName("name") val name: String,
    @SerializedName("category") val category: String,
    @SerializedName("date") val date: Int,
    @SerializedName("time1") val time1: String,
    @SerializedName("time2") val time2: String,
    @SerializedName("time3") val time3: String
)
