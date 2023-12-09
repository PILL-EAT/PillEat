package com.example.pilleat.all.page.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.pilleat.R
import com.example.pilleat.all.response.UserResponse
import com.example.pilleat.all.response.UserResult
import com.example.pilleat.all.service.UserService
import com.example.pilleat.all.view.UserDeleteView
import com.example.pilleat.databinding.ActivitySettingBinding
import com.google.gson.Gson
import retrofit2.Response

class SettingPage: AppCompatActivity(), UserDeleteView {
    private lateinit var binding: ActivitySettingBinding
    private lateinit var userResult: UserResult

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        userResult = getUserData()!!
        deleteUserInfo(userResult.id)

        binding.settingUpdateBtn.setOnClickListener {
            update()
        }

        binding.settingLogoutBtn.setOnClickListener {
            logout()
            logoutMethod()
        }

        binding.settingDeleteBtn.setOnClickListener {
            delete()
            deleteMethod()
        }
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

    private fun update() {
        val intent = Intent(this@SettingPage, UpdatePage::class.java)
        startActivity(intent)
    }

    private fun logout() {
        val inflater = getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view = inflater.inflate(R.layout.dialog_logout, null)

        val alertDialog = AlertDialog.Builder(this@SettingPage)
            .setPositiveButton("예") { dialog, which ->
                // 임시 설정 -> 로그아웃 API 연결
                Toast.makeText(this@SettingPage, "로그아웃되었습니다.", Toast.LENGTH_SHORT).show()
                val intent = Intent(this@SettingPage, StartPage::class.java)
                startActivity(intent)
            }
            .setNeutralButton("아니오", null)
            .create()
        alertDialog.setView(view)
        alertDialog.show()
    }

    // 로그인 정보 지우기
    private fun logoutMethod() {
        val spf = this@SettingPage.getSharedPreferences("auth", MODE_PRIVATE)
        val editor = spf!!.edit()
        editor.remove("userId") // 'userId' 키에 저장된 값 삭제
        editor.apply()
    }

    private fun deleteUserInfo(userId: Int) {
        val userService = UserService()
        userService.setUserDeleteView(this@SettingPage)

        userService.userDelete(userId)
    }

    private fun deleteMethod() {
        val spf = this@SettingPage.getSharedPreferences("userData", MODE_PRIVATE)
        val editor = spf!!.edit()
        editor.remove("userData") // 'userData' 키에 저장된 값 삭제
        editor.apply()
    }

    private fun delete() {
        val inflater = getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view = inflater.inflate(R.layout.dialog_delete, null)

        val alertDialog = AlertDialog.Builder(this@SettingPage)
            .setPositiveButton("예") { dialog, which ->
                // 임시 설정 -> 회원탈퇴 API 연결
                Toast.makeText(this@SettingPage, "탈퇴되었습니다.", Toast.LENGTH_SHORT).show()
                val intent = Intent(this@SettingPage, StartPage::class.java)
                startActivity(intent)
            }
            .setNeutralButton("아니오", null)
            .create()
        alertDialog.setView(view)
        alertDialog.show()
    }

    override fun onDeleteSuccess(code: Int) {
        Log.d("SettingPage-DELETE-SUCCESS", code.toString())
    }

    override fun onDeleteFailure(response: Response<UserResponse>) {
        Log.d("SettingPage-DELETE-FAILURE", response.message())
    }
}