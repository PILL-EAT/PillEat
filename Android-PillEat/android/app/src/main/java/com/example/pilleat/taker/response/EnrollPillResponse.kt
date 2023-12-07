package com.example.pilleat.taker.response

import com.google.gson.annotations.SerializedName

data class EnrollPillResponse(
    @SerializedName("isSuccess") val isSuccess: Boolean,
    @SerializedName("code") val code: Int,
    @SerializedName("message") val message: String
)
