package com.example.pilleat.taker.view

import com.example.pilleat.taker.response.EnrollPillResponse

interface EnrollPillDelView {
    fun onDeleteSuccess(code: Int, response: EnrollPillResponse)
    fun onDeleteFailure(code: Int, message: String)
}