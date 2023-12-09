package com.example.pilleat.manager.page.activity

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pilleat.databinding.ActivityUserinfoBinding
import com.example.pilleat.manager.response.UserInfo
import com.example.pilleat.manager.rvadapter.UserInfoRVAdapter
import com.example.pilleat.manager.service.UserInfoService
import com.example.pilleat.manager.view.UserInfoView

class UserInfoPage: AppCompatActivity(), UserInfoView {
    private lateinit var binding: ActivityUserinfoBinding
    private lateinit var userInfoRVAdapter: UserInfoRVAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserinfoBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun onStart() {
        super.onStart()
        getUserInfo()
    }

    private fun initRecyclerView() {
        userInfoRVAdapter = UserInfoRVAdapter()
        binding.userInfoRv.adapter = userInfoRVAdapter
        binding.userInfoRv.setHasFixedSize(true)
        binding.userInfoRv.layoutManager = LinearLayoutManager(this@UserInfoPage, LinearLayoutManager.VERTICAL, false)
    }

    private fun getUserInfo() {
        val userInfoService = UserInfoService()
        userInfoService.setUserInfoView(this@UserInfoPage)

        // api 연결 시, 데이터 가져오기
        userInfoService.getUserInfo()
    }

    override fun onGetUserInfoSuccess(code: Int, result: ArrayList<UserInfo>) {
        initRecyclerView()
        userInfoRVAdapter.setData(result)
    }

    override fun onGetUserInfoFailure(code: Int, message: String) {
        Toast.makeText(this@UserInfoPage, message, Toast.LENGTH_SHORT).show()
    }
}