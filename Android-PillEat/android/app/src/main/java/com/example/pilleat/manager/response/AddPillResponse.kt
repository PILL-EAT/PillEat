package com.example.pilleat.manager.response

import com.google.gson.annotations.SerializedName

data class AddPillResponse(
    @SerializedName(value = "isSuccess") val isSuccess: Boolean,
    @SerializedName(value = "code") val code: Int,
    @SerializedName(value = "message") val message: String
)