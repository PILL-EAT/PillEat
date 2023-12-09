package com.example.pilleat.manager.retrofit_interface

import com.example.pilleat.manager.response.UserInfoResponse
import retrofit2.Call
import retrofit2.http.*

interface ManagerRetrofitInterface {
    @GET("/manager/user-list")
    fun getUserInfo(): Call<UserInfoResponse>
}