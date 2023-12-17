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
import com.example.pilleat.WebSocketManager
import com.example.pilleat.all.response.UserResponse
import com.example.pilleat.all.response.UserResult
import com.example.pilleat.all.service.UserService
import com.example.pilleat.all.view.UserDeleteView
import com.example.pilleat.databinding.ActivitySettingBinding
import com.google.gson.Gson
import org.json.JSONObject
import retrofit2.Response

class SettingPage: AppCompatActivity(), UserDeleteView {
    private lateinit var binding: ActivitySettingBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.settingUpdateBtn.setOnClickListener {
            update()
        }

        binding.settingLogoutBtn.setOnClickListener {
            logout()
        }

        binding.settingDeleteBtn.setOnClickListener {
            deleteUserInfo(getData())
        }
    }

    private fun getData(): Int {
        val getIntent = intent
        val getData = getIntent.getIntExtra("takerId", 0)
        Log.d("SettingPage-takerId", getData.toString())
        return getData
    }

    private fun getData2(): Int {
        val getIntent = intent
        val getData = getIntent.getIntExtra("protectorId", 0)
        Log.d("SettingPage-protectorId", getData.toString())
        return getData
    }

    private fun getTakerType(): String {
        val getIntent = intent
        val getData = getIntent.getStringExtra("takerType")!!
        return getData
    }

    private fun update() {
        if(getData() == 0) {
            val intent = Intent(this@SettingPage, UpdatePage::class.java)
            intent.putExtra("userId", getData2())
            intent.putExtra("takerType", getTakerType())
            startActivity(intent)
        } else {
            val intent = Intent(this@SettingPage, UpdatePage::class.java)
            intent.putExtra("userId", getData())
            intent.putExtra("takerType", getTakerType())
            startActivity(intent)
        }
    }

    private fun logout() {
        val inflater = getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view = inflater.inflate(R.layout.dialog_logout, null)

        val alertDialog = AlertDialog.Builder(this@SettingPage)
            .setPositiveButton("예") { dialog, which ->
                // WebSocket 연결 닫기
                WebSocketManager.getInstance(this).closeWebSocket()

                Toast.makeText(this@SettingPage, "로그아웃되었습니다.", Toast.LENGTH_SHORT).show()
                val intent = Intent(this@SettingPage, StartPage::class.java)
                startActivity(intent)

            }
            .setNeutralButton("아니오", null)
            .create()
        alertDialog.setView(view)
        alertDialog.show()
    }

    private fun deleteUserInfo(userId: Int) {
        val userService = UserService()
        userService.setUserDeleteView(this@SettingPage)

        userService.userDelete(userId)
    }

    override fun onDeleteSuccess(code: Int) {
        Toast.makeText(this@SettingPage, "탈퇴 처리되었습니다.", Toast.LENGTH_SHORT).show()
        Log.d("USER-DELETE-SUCCESS", code.toString())
        val intent = Intent(this@SettingPage, StartPage::class.java)
        startActivity(intent)
    }

    override fun onDeleteFailure(response: Response<UserResponse>) {
        Log.d("USER-DELETE-FAILURE", response.message())
    }
}