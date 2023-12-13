package com.example.pilleat.taker.view

import com.example.pilleat.taker.response.EnrollPillListResponse
import retrofit2.Response

interface EnrollPillListView {
    fun onEnrollPillListSuccess(code: Int, result: EnrollPillListResponse)
    fun onEnrollPillListFailure(code: Int, message: String)
}