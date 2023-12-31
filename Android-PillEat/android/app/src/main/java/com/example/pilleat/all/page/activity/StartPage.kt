package com.example.pilleat.all.page.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.pilleat.WebSocketManager
import com.example.pilleat.databinding.ActivityStartBinding
import com.example.pilleat.manager.page.activity.MainManagerPage
import com.example.pilleat.protector.page.activity.MainProtectorPage
import com.example.pilleat.taker.page.activity.MainTakerPage
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okhttp3.WebSocket
import okhttp3.WebSocketListener

class StartPage: AppCompatActivity() {
    private lateinit var binding: ActivityStartBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStartBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.startSearchBtn.setOnClickListener {
            startSearchPage()
        }

        binding.startLoginBtn.setOnClickListener {
            startLoginPage()
        }
    }

    // 약 검색 화면으로 이동
    private fun startSearchPage() {
        val intent = Intent(this@StartPage, SearchPage::class.java)
        startActivity(intent)
    }

    // 로그인 화면으로 이동
    private fun startLoginPage() {
        val intent = Intent(this@StartPage, LoginPage::class.java)
        startActivity(intent)
    }
}