package com.example.pilleat.all.response

import com.google.gson.annotations.SerializedName

data class UserResponse(
    @SerializedName("isSuccess") val isSuccess: Boolean,
    @SerializedName("code") val code: Int,
    @SerializedName("message") val message: String,
    @SerializedName("result") val result: ArrayList<UserResult>
)

data class UserResult(
    @SerializedName("email") var email: String,
    @SerializedName("password") var password: String,
    @SerializedName("name") var name: String,
    @SerializedName("birth") var birth: String,
    @SerializedName("phone") var phone: String,
    @SerializedName("mode") val mode: String,
    @SerializedName("join_date") val join_date: String,
    @SerializedName("id") val id: Int
)
