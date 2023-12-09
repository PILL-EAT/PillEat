package com.example.pilleat.all.view

import com.example.pilleat.all.response.LoginResponse
import com.example.pilleat.all.response.Result
import retrofit2.Response

interface LoginView {
    fun onLoginSuccess(code: Int, result: Result)
    fun onLoginFailure(response: Response<LoginResponse>)
}