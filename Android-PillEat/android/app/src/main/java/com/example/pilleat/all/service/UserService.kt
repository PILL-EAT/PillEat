package com.example.pilleat.all.service

import android.util.Log
import com.example.pilleat.all.response.UserResponse
import com.example.pilleat.all.response.UserResult
import com.example.pilleat.all.retrofit_interface.UserRetrofitInterface
import com.example.pilleat.all.view.UserDeleteView
import com.example.pilleat.all.view.UserReadView
import com.example.pilleat.all.view.UserUpdateView
import com.example.pilleat.getRetrofit
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserService {
    private lateinit var userReadView: UserReadView
    private lateinit var userUpdateView: UserUpdateView
    private lateinit var userDeleteView: UserDeleteView

    fun setUserReadView(userReadView: UserReadView) {
        this.userReadView = userReadView
    }

    fun setUserUpdateView(userUpdateView: UserUpdateView) {
        this.userUpdateView = userUpdateView
    }

    fun setUserDeleteView(userDeleteView: UserDeleteView) {
        this.userDeleteView = userDeleteView
    }

    fun userRead(userId: Int) {
        val userService = getRetrofit().create(UserRetrofitInterface::class.java)
        userService.getUserInfo(userId).enqueue(object : Callback<UserResponse> {
            override fun onResponse(call: Call<UserResponse>, response: Response<UserResponse>) {
                Log.d("USER-READ-SUCCESS", response.toString())

                val resp: UserResponse = response.body()!!
                when(resp.code) {
                    200 -> userReadView.onReadSuccess(resp.code)
                    else -> userReadView.onReadFailure(response)
                }
            }

            override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                Log.d("USER-READ-FAILURE", t.toString())
            }
        })
    }

    fun userUpdate(userResult: UserResult, userId: Int) {
        val userService = getRetrofit().create(UserRetrofitInterface::class.java)
        userService.putUserUpdate(userResult, userId).enqueue(object : Callback<UserResponse> {
            override fun onResponse(call: Call<UserResponse>, response: Response<UserResponse>) {
                Log.d("USER-UPDATE-SUCCESS", response.toString())

                val resp: UserResponse = response.body()!!
                when(resp.code) {
                    200 -> userUpdateView.onUpdateSuccess(resp.code)
                    else -> userUpdateView.onUpdateFailure(response)
                }
            }

            override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                Log.d("USER-UPDATE-FAILURE", t.toString())
            }
        })
    }

    fun userDelete(userId: Int) {
        val userService = getRetrofit().create(UserRetrofitInterface::class.java)
        userService.deleteUserInfo(userId).enqueue(object : Callback<UserResponse> {
            override fun onResponse(call: Call<UserResponse>, response: Response<UserResponse>) {
                Log.d("USER-DELETE-SUCCESS", response.toString())

                val resp: UserResponse = response.body()!!
                when(resp.code) {
                    200 -> userDeleteView.onDeleteSuccess(resp.code)
                    else -> userDeleteView.onDeleteFailure(response)
                }
            }

            override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                Log.d("USER-DELETE-FAILURE", t.toString())
            }
        })
    }
}