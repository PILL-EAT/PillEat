package com.example.pilleat.taker.response

import com.google.gson.annotations.SerializedName

data class EnrollRecordResponse(
    @SerializedName("isSuccess") val isSuccess: Boolean,
    @SerializedName("code") val code: Int,
    @SerializedName("message") val message: String,
    @SerializedName("result") val result: Records
)

data class Records(
    @SerializedName("list") var list: ArrayList<RecordResult>
)

data class RecordResult(
    @SerializedName("userId") val userId: Int,
    @SerializedName("drugId") val drugId: Int,
    @SerializedName("name") val name: String,
    @SerializedName("time") val time: String,
    @SerializedName("finishYN") var finishYN: Int
)
