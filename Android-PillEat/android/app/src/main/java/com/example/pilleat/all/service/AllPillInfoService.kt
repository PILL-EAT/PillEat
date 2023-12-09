package com.example.pilleat.all.service

import android.util.Log
import com.example.pilleat.all.response.AllPillInfoResponse
import com.example.pilleat.all.retrofit_interface.AllPillInfoRetrofitInterface
import com.example.pilleat.all.view.AllPillInfoView
import com.example.pilleat.getRetrofit2
import retrofit2.*

class AllPillInfoService {
    private lateinit var allPillInfoView: AllPillInfoView

    fun setAddPillInfoView(allPillInfoView: AllPillInfoView) {
        this.allPillInfoView = allPillInfoView
    }

    fun getData(itemName: String) {
        val allPillInfoService = getRetrofit2().create(AllPillInfoRetrofitInterface::class.java)

        allPillInfoService.getData(itemName = itemName).enqueue(object : Callback<AllPillInfoResponse> {
            override fun onResponse(call: Call<AllPillInfoResponse>, response: Response<AllPillInfoResponse>) {
                val resultCode = response.body()?.header?.resultCode
                val resultMsg = response.body()?.header?.resultMsg
                Log.d("CODE", response.body().toString())
                if(resultCode == "00") {
                    val allPillInfoResponse: AllPillInfoResponse = response.body()!!
                    Log.d("MAIN-RESPONSE", allPillInfoResponse.toString())

                    allPillInfoView.onGetDataSuccess(allPillInfoResponse)
                } else {
                    Log.d("MAIN-RESPONSE", resultMsg.toString())
                }
            }

            override fun onFailure(call: Call<AllPillInfoResponse>, t: Throwable) {
                allPillInfoView.onGetDataFailure("네트워크 오류가 발생했습니다.")
            }
        })
    }
}