package com.example.pilleat.manager.response

import com.google.gson.annotations.SerializedName

data class UserInfoResponse(
    @SerializedName("isSuccess") val isSuccess: Boolean,
    @SerializedName("code") val code: Int,
    @SerializedName("message") val message: String,
    @SerializedName("result") val result: Users
)

data class Users(
    @SerializedName("users") val users: ArrayList<UserInfo>
)

data class UserInfo (
    @SerializedName("name") val name: String,
    @SerializedName("time") val time: String,
    @SerializedName("birth") val birth: String,
    @SerializedName("mode") val mode: String
)
