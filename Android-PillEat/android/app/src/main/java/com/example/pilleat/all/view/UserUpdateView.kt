package com.example.pilleat.all.view

import com.example.pilleat.all.response.UserResponse
import retrofit2.Response

interface UserUpdateView {
    fun onUpdateSuccess(code: Int, response: UserResponse)
    fun onUpdateFailure(response: Response<UserResponse>)
}