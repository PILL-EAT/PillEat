package com.example.pilleat.taker.service

import android.util.Log
import com.example.pilleat.getRetrofit
import com.example.pilleat.taker.response.EnrollPillResponse
import com.example.pilleat.taker.retrofit_interface.TakerRetrofitInterface
import com.example.pilleat.taker.table.EnrollDevice
import com.example.pilleat.taker.view.EnrollDeviceView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class EnrollDeviceService {
    private lateinit var enrollDeviceView: EnrollDeviceView

    fun setEnrollDeviceView(enrollDeviceView: EnrollDeviceView) {
        this.enrollDeviceView = enrollDeviceView
    }

    fun enrollDevice(enrollDevice: EnrollDevice, takerId: Int) {
        val enrollDeviceService = getRetrofit().create(TakerRetrofitInterface::class.java)
        enrollDeviceService.setIotDevice(enrollDevice, takerId).enqueue(object: Callback<EnrollPillResponse> {
            override fun onResponse(
                call: Call<EnrollPillResponse>,
                response: Response<EnrollPillResponse>
            ) {
                val resp: EnrollPillResponse = response.body()!!
                Log.d("ENROLL-DEVICE-SUCCESS", resp.toString())
                when(resp.code) {
                    200 -> enrollDeviceView.onSetIotDeviceSuccess(resp.code)
                    else -> enrollDeviceView.onSetIotDeviceFailure(resp.code, resp.message)
                }
            }

            override fun onFailure(call: Call<EnrollPillResponse>, t: Throwable) {
                Log.d("ENROLL-DEVICE-FAILURE", t.toString())
            }

        })
    }
    /*private lateinit var enrollPillView: EnrollPillView

    fun setEnrollPillView(enrollPillView: EnrollPillView) {
        this.enrollPillView = enrollPillView
    }

    fun enrollPill(enrollPill: EnrollPill, userId: Int) {
        val enrollPillService = getRetrofit().create(TakerRetrofitInterface::class.java)
        enrollPillService.enrollPill(enrollPill, userId).enqueue(object : Callback<EnrollPillResponse> {
            override fun onResponse(call: Call<EnrollPillResponse>, response: Response<EnrollPillResponse>) {
                Log.d("약 등록 API", response.toString())
                val resp: EnrollPillResponse = response.body()!!
                when(resp.code) {
                    200 -> enrollPillView.onEnrollPillSuccess()
                    else -> enrollPillView.onEnrollPillFailure(response)
                }
            }

            override fun onFailure(call: Call<EnrollPillResponse>, t: Throwable) {
                Log.d("ENROLL_PILL_FAILURE", t.message!!)
            }
        })
    }*/
}