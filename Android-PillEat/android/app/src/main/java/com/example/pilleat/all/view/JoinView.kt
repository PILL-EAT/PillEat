package com.example.pilleat.all.view

import com.example.pilleat.all.response.JoinResponse
import retrofit2.Response

interface JoinView {
    fun onJoinSuccess()
    fun onJoinFailure(response: Response<JoinResponse>)
}