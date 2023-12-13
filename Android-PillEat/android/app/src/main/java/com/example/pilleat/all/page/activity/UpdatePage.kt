package com.example.pilleat.all.page.activity

import android.os.Bundle
import android.util.Log
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUpdateBinding.inflate(layoutInflater)
        setContentView(binding.root)

        readUserInfo(getUserId())

        binding.updateBtn.setOnClickListener {
            updateUserInfo(getUserId())
        }
    }

    private fun getUserId(): Int {
        val getIntent = intent
        val getData = getIntent.getIntExtra("userId", 0)
        Log.d("IDIDID", getData.toString())
        return getData
    }

    private fun initData(result: UserResponse) {
        binding.updateNameEt.setText(result.result.name)
        binding.updatePwEt.setText(result.result.password)
        binding.updateEmailEt.setText(result.result.email)
        binding.updateBirthEt.setText(result.result.birth)
        binding.updatePhoneEt.setText(result.result.phone)
        binding.joinModeTv.setText(result.result.mode)

        if(result.result.mode == "taker") {
            binding.joinModeCb.isChecked = false
        } else {
            binding.joinModeCb.isChecked = true
        }
    }

    private fun readUserInfo(userId: Int) {
        val userService = UserService()
        userService.setUserReadView(this@UpdatePage)

        userService.userRead(userId)
    }

    private fun updateUserInfo(userId: Int) {
        val name = binding.updateNameEt.text.toString()
        val email = binding.updateEmailEt.text.toString()
        val password = binding.updatePwEt.text.toString()
        val phone = binding.updatePhoneEt.text.toString()
        val birth = binding.updateBirthEt.text.toString()

        val result = UserResult(email, password, name, birth, phone, "", "", getUserId())

        Log.d("djfksdjfl", result.toString())
        val userService = UserService()
        userService.setUserUpdateView(this@UpdatePage)

        userService.userUpdate(result, userId)
    }

    override fun onReadSuccess(code: Int, response: UserResponse) {
        initData(response)
        Log.d("USER-INFO", code.toString())
    }

    override fun onReadFailure(response: Response<UserResponse>) {
        Log.d("USER-INFO-FAIL", response.message())
    }

    override fun onUpdateSuccess(code: Int, response: UserResponse) {
        Toast.makeText(this@UpdatePage, "정보가 수정되었습니다", Toast.LENGTH_SHORT).show()
        Log.d("USER-INFO-UPDATE", code.toString())
    }

    override fun onUpdateFailure(response: Response<UserResponse>) {
        Log.d("USER-INFO-UPDATE", response.message())
    }
}