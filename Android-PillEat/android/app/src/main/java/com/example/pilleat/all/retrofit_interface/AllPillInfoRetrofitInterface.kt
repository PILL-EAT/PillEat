package com.example.pilleat.all.retrofit_interface

import com.example.pilleat.all.response.AllPillInfoResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface AllPillInfoRetrofitInterface {
    companion object {
        private const val authKey = "+YTZbdAw6Wm3deZyU/59WCuZD+pTuzjQkNkU9hEafCiXW7KX5j77A3p8U+zoKSI568aesezu2wp/BinOckcRvA=="
    }

    @GET("getMdcEssntlItemList03")
    fun getData(
        @Query("serviceKey")
        serviceKey: String = authKey,
        @Query("pageNo")
        pageNo: Int = 1,
        @Query("numOfRows")
        numOfRows: Int = 10,
        @Query("type")
        type: String = "json",
        @Query("essntl_item_name")
        essntl_item_name: String = "요오드"
    ): Call<AllPillInfoResponse>
}