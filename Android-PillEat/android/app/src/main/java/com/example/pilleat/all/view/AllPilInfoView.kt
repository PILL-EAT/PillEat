package com.example.pilleat.all.view

import com.example.pilleat.all.response.AllPillInfoResponse

interface AllPillInfoView {
    fun onGetDataSuccess(result: AllPillInfoResponse)
    fun onGetDataFailure(message: String)
}