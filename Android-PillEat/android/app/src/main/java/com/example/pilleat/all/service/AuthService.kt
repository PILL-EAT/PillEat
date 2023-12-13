package com.example.pilleat.all.service

import android.util.Log
import com.example.pilleat.all.response.JoinResponse
import com.example.pilleat.all.response.LoginResponse
import com.example.pilleat.all.response.UserResult
import com.example.pilleat.all.view.LoginView
import com.example.pilleat.getRetrofit
import com.example.pilleat.all.retrofit_interface.AuthRetrofitInterface
import com.example.pilleat.all.table.Auth
import com.example.pilleat.all.table.User
import com.example.pilleat.all.view.JoinView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AuthService {
    private lateinit var loginView: LoginView
    private lateinit var joinView: JoinView

    fun setLoginView(loginView: LoginView) {
        this.loginView = loginView
    }

    fun setJoinView(joinView: JoinView) {
        this.joinView = joinView
    }

    fun login(auth: Auth) {
        val authService = getRetrofit().create(AuthRetrofitInterface::class.java)
        authService.login(auth).enqueue(object: Callback<LoginResponse> {
            // login에 성공했을 경우
            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                Log.d("LOGIN_SUCCESS", response.toString())

                // response의 body값 가져오기
                val resp: LoginResponse = response.body()!!
                Log.d("login_code", resp.code.toString())

                when(val code = resp.code) {
                    1000 -> loginView.onLoginSuccess(code, resp.result!!)
                    else -> loginView.onLoginFailure(response)
                }
            }

            // login에 실패했을 경우
            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                Log.d("LOGIN_FAILURE", t.toString())
            }
        })
        Log.d("LOGIN", "HELLO")
    }

    fun join(user: User) {
        val authService = getRetrofit().create(AuthRetrofitInterface::class.java)
        authService.join(user).enqueue(object: Callback<JoinResponse> {
            // join에 성공했을 경우
            override fun onResponse(call: Call<JoinResponse>, response: Response<JoinResponse>) {
                Log.d("JOIN-SUCCESS", response.toString())

                // response의 body 값 가져오기
                val resp: JoinResponse = response.body()!!
                when(resp.code) {
                    1000 -> joinView.onJoinSuccess()
                    else -> joinView.onJoinFailure(response)
                }
            }

            override fun onFailure(call: Call<JoinResponse>, t: Throwable) {
                Log.d("JOIN-FAILURE", t.toString())
            }

        })
    }
}