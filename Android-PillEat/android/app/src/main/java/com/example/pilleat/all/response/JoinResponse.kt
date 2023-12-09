package com.example.pilleat.all.response

import com.google.gson.annotations.SerializedName

data class JoinResponse(
    @SerializedName("isSuccess") val isSuccess: Boolean,
    @SerializedName("code") val code: Int,
    @SerializedName("message") val message: String
)