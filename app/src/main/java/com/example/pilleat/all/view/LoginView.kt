package com.example.pilleat.all.view

import com.example.pilleat.all.response.AuthResponse
import com.example.pilleat.all.response.Result
import retrofit2.Response

interface LoginView {
    fun onLoginSuccess(code: Int, result: Result)
    fun onLoginFailure(response: Response<AuthResponse>)
}