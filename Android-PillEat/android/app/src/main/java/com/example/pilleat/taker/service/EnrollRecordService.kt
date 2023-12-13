package com.example.pilleat.taker.service

import android.util.Log
import com.example.pilleat.getRetrofit
import com.example.pilleat.taker.response.EnrollPillListResponse
import com.example.pilleat.taker.response.EnrollPillResponse
import com.example.pilleat.taker.response.EnrollRecordResponse
import com.example.pilleat.taker.retrofit_interface.TakerRetrofitInterface
import com.example.pilleat.taker.table.EnrollPill
import com.example.pilleat.taker.view.EnrollPillView
import com.example.pilleat.taker.view.EnrollRecordView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class EnrollRecordService {
    private lateinit var enrollRecordView: EnrollRecordView

    fun setEnrollRecordView(enrollRecordView: EnrollRecordView) {
        this.enrollRecordView = enrollRecordView
    }

    fun enrollRecord(userId: Int, date: String) {
        val enrollRecordService = getRetrofit().create(TakerRetrofitInterface::class.java)
        enrollRecordService.getRecord(userId, date).enqueue(object : Callback<EnrollRecordResponse> {
            override fun onResponse(call: Call<EnrollRecordResponse>, response: Response<EnrollRecordResponse>
            ) {
                Log.d("RECORD-SUCCESS", response.toString())
                val resp: EnrollRecordResponse = response.body()!!
                when(resp.code) {
                    200 -> enrollRecordView.onGetRecordSuccess(resp.code, resp)
                    else -> enrollRecordView.onGetRecordFailure(resp.code, resp.message)
                }
            }

            override fun onFailure(call: Call<EnrollRecordResponse>, t: Throwable) {
                Log.d("RECORD-FAILURE", t.toString())
            }
        })
    }
}