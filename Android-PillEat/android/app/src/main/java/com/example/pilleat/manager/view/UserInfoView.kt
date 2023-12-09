package com.example.pilleat.manager.view

import com.example.pilleat.manager.response.UserInfo

interface UserInfoView {
    fun onGetUserInfoSuccess(code: Int, result: ArrayList<UserInfo>)
    fun onGetUserInfoFailure(code: Int, message: String)
}