package com.example.pilleat.protector.retrofit_interface

import com.example.pilleat.protector.response.SetTakerResponse
import retrofit2.Call
import retrofit2.http.*

interface ProtectorRetrofitInterface {
    @POST("protector/input-taker/{protectorId}")
    fun setTaker(@Body phone: String, @Path("protectorId") protectorId: Int): Call<SetTakerResponse>
}