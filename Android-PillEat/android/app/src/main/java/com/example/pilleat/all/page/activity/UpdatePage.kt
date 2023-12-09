package com.example.pilleat.all.page.activity

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.pilleat.all.response.UserResponse
import com.example.pilleat.all.response.UserResult
import com.example.pilleat.all.service.UserService
import com.example.pilleat.all.view.UserReadView
import com.example.pilleat.all.view.UserUpdateView
import com.example.pilleat.databinding.ActivityUpdateBinding
import com.google.gson.Gson
import retrofit2.Response

class UpdatePage: AppCompatActivity(), UserReadView, UserUpdateView {
    private lateinit var binding: ActivityUpdateBinding
    private lateinit var userResult: UserResult

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUpdateBinding.inflate(layoutInflater)
        setContentView(binding.root)

        userResult = getUserData()!!
        initData(userResult)
        readUserInfo(userResult.id)
        updateData(userResult)
        updateUserInfo(userResult, userResult.id)
    }

    private fun initData(result: UserResult) {
        binding.updateNameEt.setText(result.name)
        binding.updatePwEt.setText(result.password)
        binding.updateEmailEt.setText(result.email)
        binding.updateBirthEt.setText(result.birth)
        binding.updatePhoneEt.setText(result.phone)
        binding.joinModeTv.setText(result.mode)

        if(result.mode == "taker") {
            binding.joinModeCb.isChecked = false
        } else {
            binding.joinModeCb.isChecked = true
        }
    }

    private fun updateData(result: UserResult) {
        result.name = binding.updateNameEt.text.toString()
        result.email = binding.updateEmailEt.text.toString()
        result.password = binding.updatePwEt.text.toString()
        result.phone = binding.updatePhoneEt.text.toString()
        result.birth = binding.updateBirthEt.text.toString()
    }

    private fun getUserData(): UserResult? {
        val spf = getSharedPreferences("userData", MODE_PRIVATE)
        val userJson = spf.getString("userData", null)

        return if (userJson != null) {
            val gson = Gson()
            gson.fromJson(userJson, UserResult::class.java)
        } else {
            null
        }
    }

    private fun readUserInfo(userId: Int) {
        val userService = UserService()
        userService.setUserReadView(this@UpdatePage)

        userService.userRead(userId)
    }

    private fun updateUserInfo(userResult: UserResult, userId: Int) {
        val userService = UserService()
        userService.setUserUpdateView(this@UpdatePage)

        userService.userUpdate(userResult, userId)
    }

    override fun onReadSuccess(code: Int) {
        Toast.makeText(this@UpdatePage, "정보를 불러왔습니다.", Toast.LENGTH_SHORT).show()
    }

    override fun onReadFailure(response: Response<UserResponse>) {
        Toast.makeText(this@UpdatePage, "정보를 불러오지 못했습니다.", Toast.LENGTH_SHORT).show()
    }

    override fun onUpdateSuccess(code: Int) {
        Toast.makeText(this@UpdatePage, "정보가 수정되었습니다.", Toast.LENGTH_SHORT).show()
    }

    override fun onUpdateFailure(response: Response<UserResponse>) {
        Toast.makeText(this@UpdatePage, "정보가 수정되지 않았습니다.", Toast.LENGTH_SHORT).show()
    }
}