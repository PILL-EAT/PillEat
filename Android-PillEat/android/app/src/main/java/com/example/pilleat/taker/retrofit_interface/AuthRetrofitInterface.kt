package com.example.pilleat.taker.retrofit_interface

import com.example.pilleat.all.response.AuthResponse
import com.example.pilleat.all.table.User
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthRetrofitInterface {
    @POST("/user/login")
    fun login(@Body user: User): Call<AuthResponse>
}