package com.example.pilleat.all.response

import com.google.gson.annotations.SerializedName

data class AuthResponse(
    @SerializedName("isSuccess") val isSuccess: Boolean,
    @SerializedName("code") val code: Int,
    @SerializedName("message") val message: String,
    @SerializedName("result") val result: Result?
)

data class Result(
    @SerializedName("userId") val userId: Int,
    @SerializedName("jwt") val jwt: String
)
