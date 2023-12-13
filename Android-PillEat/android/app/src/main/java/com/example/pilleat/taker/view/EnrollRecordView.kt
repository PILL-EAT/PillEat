package com.example.pilleat.taker.view

import com.example.pilleat.taker.response.EnrollRecordResponse

interface EnrollRecordView {
    fun onGetRecordSuccess(code: Int, response: EnrollRecordResponse)
    fun onGetRecordFailure(code: Int, message: String)
}