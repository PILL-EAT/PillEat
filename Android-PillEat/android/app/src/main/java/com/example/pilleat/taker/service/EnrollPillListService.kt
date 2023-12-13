package com.example.pilleat.taker.service

import android.util.Log
import com.example.pilleat.getRetrofit
import com.example.pilleat.taker.response.EnrollPillListResponse
import com.example.pilleat.taker.response.EnrollPillResponse
import com.example.pilleat.taker.retrofit_interface.TakerRetrofitInterface
import com.example.pilleat.taker.view.EnrollPillDelView
import com.example.pilleat.taker.view.EnrollPillListView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.create

class EnrollPillListService {
    private lateinit var enrollPillListView: EnrollPillListView
    private lateinit var enrollPillDelView: EnrollPillDelView

    fun setEnrollPillListView(enrollPillListView: EnrollPillListView) {
        this.enrollPillListView = enrollPillListView
    }

    fun setEnrollPillDeleteView(enrollPillDelView: EnrollPillDelView) {
        this.enrollPillDelView = enrollPillDelView
    }

    fun enrollPillList(userId: Int) {
        val enrollPillListService = getRetrofit().create(TakerRetrofitInterface::class.java)
        enrollPillListService.getEnrollPillList(userId).enqueue(object: Callback<EnrollPillListResponse> {
            override fun onResponse(call: Call<EnrollPillListResponse>, response: Response<EnrollPillListResponse>) {
                Log.d("PILL-LIST-SUCCESS", response.body()!!.result.drugs.toString())
                val resp: EnrollPillListResponse = response.body()!!
                when(resp.code) {
                    200 -> enrollPillListView.onEnrollPillListSuccess(resp.code, resp)
                    else -> enrollPillListView.onEnrollPillListFailure(resp.code, resp.message)
                }
            }

            override fun onFailure(call: Call<EnrollPillListResponse>, t: Throwable) {
                Log.d("PILL-LIST-FAILURE", t.message!!)
            }
        })
    }

    fun enrollPillDelete(drugId: Int) {
        val enrollPillListService = getRetrofit().create(TakerRetrofitInterface::class.java)
        enrollPillListService.deleteEnrollPill(drugId).enqueue(object : Callback<EnrollPillResponse> {
            override fun onResponse(call: Call<EnrollPillResponse>, response: Response<EnrollPillResponse>) {
                val resp: EnrollPillResponse = response.body()!!
                when(resp.code) {
                    200 -> enrollPillDelView.onDeleteSuccess(resp.code, resp)
                    else -> enrollPillDelView.onDeleteFailure(resp.code, resp.message)
                }
            }

            override fun onFailure(call: Call<EnrollPillResponse>, t: Throwable) {
                Log.d("ENROLL-PILL-DELETE-FAILURE", t.toString())
            }

        })
    }
}