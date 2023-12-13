package com.example.pilleat.protector.response

import com.google.gson.annotations.SerializedName

data class SetTakerResponse(
    @SerializedName("isSuccess") val isSuccess: Boolean,
    @SerializedName("code") val code: Int,
    @SerializedName("message") val message: String
)
