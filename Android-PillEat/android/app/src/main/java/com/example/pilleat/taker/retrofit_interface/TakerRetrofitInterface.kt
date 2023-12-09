package com.example.pilleat.taker.retrofit_interface

import com.example.pilleat.taker.response.EnrollPillListResponse
import com.example.pilleat.taker.response.EnrollPillResponse
import com.example.pilleat.taker.table.EnrollPill
import retrofit2.Call
import retrofit2.http.*

interface TakerRetrofitInterface {
    @POST("taker/drug/enroll/{userId}")
    fun enrollPill(@Body enrollPill: EnrollPill, @Path("userId") userId: Int): Call<EnrollPillResponse>

    @GET("taker/drug/enroll-list/{userId}")
    fun getEnrollPillList(@Path("userId") userId: Int): Call<EnrollPillListResponse>

}