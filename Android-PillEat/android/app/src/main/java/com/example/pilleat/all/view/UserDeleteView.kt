package com.example.pilleat.all.view

import com.example.pilleat.all.response.UserResponse
import retrofit2.Response

interface UserDeleteView {
    fun onDeleteSuccess(code: Int)
    fun onDeleteFailure(response: Response<UserResponse>)
}