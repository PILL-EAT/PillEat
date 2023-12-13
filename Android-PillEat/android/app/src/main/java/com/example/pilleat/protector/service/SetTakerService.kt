package com.example.pilleat.protector.service

import android.util.Log
import com.example.pilleat.getRetrofit
import com.example.pilleat.protector.response.SetTakerResponse
import com.example.pilleat.protector.retrofit_interface.ProtectorRetrofitInterface
import com.example.pilleat.protector.view.SetTakerView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.create

class SetTakerService {
    private lateinit var setTakerView: SetTakerView

    fun initSetTakerView(setTakerView: SetTakerView) {
        this.setTakerView = setTakerView
    }

    fun inputTaker(phone: String, protectorId: Int) {
        val setTakerService = getRetrofit().create(ProtectorRetrofitInterface::class.java)
        setTakerService.setTaker(phone, protectorId).enqueue(object: Callback<SetTakerResponse> {
            override fun onResponse(call: Call<SetTakerResponse>, response: Response<SetTakerResponse>) {
                Log.d("SET-TAKER-SUCCESS", response.toString())

                val resp: SetTakerResponse = response.body()!!
                when(resp.code) {
                    200 -> setTakerView.onSetTakerSuccess(resp.code)
                    else -> setTakerView.onSetTakerFailure(resp.code, resp.message)
                }
            }

            override fun onFailure(call: Call<SetTakerResponse>, t: Throwable) {
                Log.d("SET-TAKER-FAILURE", t.toString())
            }
        })
    }
}