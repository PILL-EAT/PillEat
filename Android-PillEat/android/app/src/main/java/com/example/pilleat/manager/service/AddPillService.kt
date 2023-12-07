package com.example.pilleat.manager.service

import android.util.Log
import com.example.pilleat.getRetrofit
import com.example.pilleat.manager.response.AddPillResponse
import com.example.pilleat.manager.retrofit_interface.ManagerRetrofitInterface
import com.example.pilleat.manager.table.AddPill
import com.example.pilleat.manager.view.AddPillView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AddPillService {
    private lateinit var addPillView: AddPillView

    fun setAddPillView(addPillView: AddPillView) {
        this.addPillView = addPillView
    }

    fun addPill(addPill: AddPill) {
        val addPillService = getRetrofit().create(ManagerRetrofitInterface::class.java)
        addPillService.addPill(addPill).enqueue(object : Callback<AddPillResponse> {
            override fun onResponse(call: Call<AddPillResponse>, response: Response<AddPillResponse>) {
                when(response.code()) {
                    200 -> addPillView.onAddPillSuccess()
                    else -> addPillView.onAddPillFailure(response)
                }
            }

            override fun onFailure(call: Call<AddPillResponse>, t: Throwable) {
                Log.d("ADD-PILL-FAILURE", t.message!!)
            }
        })
    }
}
