package com.example.pilleat.all.service

import android.util.Log
import android.widget.Toast
import com.example.pilleat.all.response.AllPillInfoResponse
import com.example.pilleat.all.retrofit_interface.AllPillInfoRetrofitInterface
import com.example.pilleat.all.view.AllPillInfoView
import com.example.pilleat.getRetrofit2
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AllPillInfoService {
    private lateinit var allPillInfoView: AllPillInfoView

    fun setAddPillInfoView(allPillInfoView: AllPillInfoView) {
        this.allPillInfoView = allPillInfoView
    }

    fun getData() {
        val allPillInfoService = getRetrofit2().create(AllPillInfoRetrofitInterface::class.java)

        allPillInfoService.getData().enqueue(object : Callback<AllPillInfoResponse> {
            override fun onResponse(call: Call<AllPillInfoResponse>, response: Response<AllPillInfoResponse>) {
                //val body = response.body()
                if(response.isSuccessful) {
                    val resultCode = response.body()?.response?.header?.resultCode
                    val resultMsg = response.body()?.response?.header?.resultMsg
                    Log.d("CODE", resultCode.toString())
                    if(resultCode == "00") {
                        val allPillInfoResponse: AllPillInfoResponse = response.body()!!
                        allPillInfoView.onGetDataSuccess(allPillInfoResponse)
                        Log.d("ALL-PILL-INFO-RESPONSE", allPillInfoResponse.toString())
                    } else {
                        Log.d("ALL-PILL-INFO-RESPONSE", "ERROR")
                    }
                } else {
                    Log.d("ALL-PILL-INFO-ERROR", response.message())
                }
            }

            override fun onFailure(call: Call<AllPillInfoResponse>, t: Throwable) {
                allPillInfoView.onGetDataFailure(t.message!!)
            }
        })
    }
}