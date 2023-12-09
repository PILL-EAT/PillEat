package com.example.pilleat.all.retrofit_interface

import com.example.pilleat.all.response.UserResponse
import com.example.pilleat.all.response.UserResult
import retrofit2.Call
import retrofit2.http.*

interface UserRetrofitInterface {
    @GET("user/user-info/{userId}")
    fun getUserInfo(@Path("userId") userId: Int): Call<UserResponse>

    @PUT("user/user-update/{userId}")
    fun putUserUpdate(@Body user: UserResult, @Path("userId") userId: Int): Call<UserResponse>

    @DELETE("user/user-delete/{userId}")
    fun deleteUserInfo(@Path("userId") userId: Int): Call<UserResponse>
}