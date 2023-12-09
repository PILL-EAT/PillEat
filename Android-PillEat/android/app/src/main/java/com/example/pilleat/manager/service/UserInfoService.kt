package com.example.pilleat.manager.service

import android.util.Log
import com.example.pilleat.getRetrofit
import com.example.pilleat.manager.page.activity.UserInfoPage
import com.example.pilleat.manager.response.UserInfoResponse
import com.example.pilleat.manager.retrofit_interface.ManagerRetrofitInterface
import com.example.pilleat.manager.view.UserInfoView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserInfoService {
    private lateinit var userInfoView: UserInfoView

    fun setUserInfoView(userInfoView: UserInfoPage) {
        this.userInfoView = userInfoView
    }

    fun getUserInfo() {
        val userInfoService = getRetrofit().create(ManagerRetrofitInterface::class.java)

        userInfoService.getUserInfo().enqueue(object: Callback<UserInfoResponse> {
            override fun onResponse(call: Call<UserInfoResponse>, response: Response<UserInfoResponse>) {
                if(response.isSuccessful) {
                    val userInfoResponse: UserInfoResponse = response.body()!!
                    Log.d("UserInfoService", "서버 응답 코드: ${response.code()}")
                    when(val code = userInfoResponse.code) {
                        200 -> {
                            userInfoView.onGetUserInfoSuccess(code, userInfoResponse.result.users)
                        }
                        else -> userInfoView.onGetUserInfoFailure(code, userInfoResponse.message)
                    }
                }
            }

            override fun onFailure(call: Call<UserInfoResponse>, t: Throwable) {
                Log.e("UserInfoService", "API 호출 실패", t)
                userInfoView.onGetUserInfoFailure(600, "네트워크 오류가 발생했습니다.")
            }
        })
    }
}