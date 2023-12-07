package com.example.pilleat.taker.retrofit_interface

import com.example.pilleat.taker.response.EnrollPillResponse
import com.example.pilleat.taker.table.EnrollPill
import retrofit2.Call
import retrofit2.http.*

interface TakerRetrofitInterface {
    @POST("/taker/drug/enroll")
    fun enrollPill(@Body enrollPill: EnrollPill): Call<EnrollPillResponse>
}