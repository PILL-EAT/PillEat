package com.example.pilleat.taker.retrofit_interface

import com.example.pilleat.taker.response.EnrollPillListResponse
import com.example.pilleat.taker.response.EnrollPillResponse
import com.example.pilleat.taker.response.EnrollRecordResponse
import com.example.pilleat.taker.table.EnrollPill
import retrofit2.Call
import retrofit2.http.*

interface TakerRetrofitInterface {
    // 복용할 약 등록
    @POST("taker/drug/enroll/{userId}")
    fun enrollPill(@Body enrollPill: EnrollPill, @Path("userId") userId: Int): Call<EnrollPillResponse>

    // 복용 완료 요청
    @POST("taker/drug/finish/{userId}")
    fun drugFinish(@Body finish: Boolean, @Path("userId") userId: Int): Call<EnrollPillResponse>

    // 등록한 약 목록 불러오기
    @GET("taker/drug/enroll-list/{userId}")
    fun getEnrollPillList(@Path("userId") userId: Int): Call<EnrollPillListResponse>

    // 등록한 약 삭제 요청
    @DELETE("taker/drug/enroll-delete/{drugId}")
    fun deleteEnrollPill(@Path("drugId") drugId: Int): Call<EnrollPillResponse>

    // 복용자 복용 기록 확인
    @GET("taker/record/{takerId}/{date}")
    fun getRecord(@Path("takerId") takerId: Int, @Path("date") date: String): Call<EnrollRecordResponse>

    // 복용 미완료 요청
    @GET("taker/drug/finish-no/{userId}")
    fun getNotFinish(@Path("userId") userId: Int): Call<EnrollPillResponse>
}