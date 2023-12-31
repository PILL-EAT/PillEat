package com.example.pilleat.all.view

import com.example.pilleat.all.response.UserResponse
import retrofit2.Response

interface UserReadView {
    fun onReadSuccess(code: Int, response: UserResponse)
    fun onReadFailure(response: Response<UserResponse>)
}