package com.example.pilleat

import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

const val BASE_URL = "https://10.0.2.2:3000/" // url
const val OPEN_API_BASE_URL = "http://apis.data.go.kr/1471000/MdcEssntlItemInfoService03/"

fun getRetrofit(): Retrofit {
    val retrofit = Retrofit.Builder().baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create()).build()

    return retrofit
}

fun getRetrofit2(): Retrofit {
    var gson= GsonBuilder().setLenient().create()
    val retrofit2 = Retrofit.Builder().baseUrl(OPEN_API_BASE_URL)
        .addConverterFactory(GsonConverterFactory.create()).build()

    return retrofit2
}