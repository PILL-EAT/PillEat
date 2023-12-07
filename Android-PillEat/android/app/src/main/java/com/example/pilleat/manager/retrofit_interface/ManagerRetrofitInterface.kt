package com.example.pilleat.manager.retrofit_interface

import com.example.pilleat.manager.response.AddPillResponse
import com.example.pilleat.manager.response.UserInfoResponse
import com.example.pilleat.manager.table.AddPill
import retrofit2.Call
import retrofit2.http.*

interface ManagerRetrofitInterface {
    @GET("/manager/user-list")
    fun getUserInfo(): Call<UserInfoResponse>

    @POST("/manager/drug/add")
    fun addPill(@Body pill: AddPill): Call<AddPillResponse>
}