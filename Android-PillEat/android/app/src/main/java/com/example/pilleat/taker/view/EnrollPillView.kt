package com.example.pilleat.taker.view

import com.example.pilleat.taker.response.EnrollPillResponse
import retrofit2.Response

interface EnrollPillView {
    fun onEnrollPillSuccess()
    fun onEnrollPillFailure(response: Response<EnrollPillResponse>)
}