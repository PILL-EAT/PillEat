package com.example.pilleat.all.service

import android.util.Log
import com.example.pilleat.all.page.activity.LoginPage
import com.example.pilleat.all.response.AuthResponse
import com.example.pilleat.all.table.User
import com.example.pilleat.all.view.LoginView
import com.example.pilleat.getRetrofit
import com.example.pilleat.all.retrofit_interface.AuthRetrofitInterface
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AuthService {
    private lateinit var loginView: LoginView

    fun setLoginView(loginView: LoginPage) {
        this.loginView = loginView
    }

    fun login(user: User) {
        val authService = getRetrofit().create(AuthRetrofitInterface::class.java)
        authService.login(user).enqueue(object: Callback<AuthResponse> {
            // login에 성공했을 경우
            override fun onResponse(call: Call<AuthResponse>, response: Response<AuthResponse>) {
                Log.d("LOGIN_SUCCESS", response.toString())

                // response의 body값 가져오기
                val resp: AuthResponse = response.body()!!
                Log.d("login_code", resp.code.toString())

                when(val code = resp.code) {
                    1000 -> loginView.onLoginSuccess(code, resp.result!!)
                    else -> loginView.onLoginFailure(response)
                }
            }

            // login에 실패했을 경우
            override fun onFailure(call: Call<AuthResponse>, t: Throwable) {
                Log.d("LOGIN_FAILURE", t.toString())
            }
        })
        Log.d("LOGIN", "HELLO")
    }
}