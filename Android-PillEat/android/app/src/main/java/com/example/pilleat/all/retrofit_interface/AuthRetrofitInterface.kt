package com.example.pilleat.all.retrofit_interface

import com.example.pilleat.all.response.LoginResponse
import com.example.pilleat.all.response.JoinResponse
import com.example.pilleat.all.table.User
import retrofit2.Call
import retrofit2.http.*

interface AuthRetrofitInterface {
    @POST("user/login")
    fun login(@Body email: String, @Body password: String): Call<LoginResponse>

    @POST("user/join")
    fun join(@Body user: User): Call<JoinResponse>
}