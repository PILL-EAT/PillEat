package com.example.pilleat.manager.view

import com.example.pilleat.manager.response.Users

interface UserInfoView {
    fun onGetUserInfoSuccess(code: Int, result: Users)
    fun onGetUserInfoFailure(code: Int, message: String)
}