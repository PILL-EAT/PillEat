package com.example.pilleat.all.page.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.pilleat.databinding.ActivityUpdateBinding

class UpdatePage: AppCompatActivity() {
    private lateinit var binding: ActivityUpdateBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUpdateBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    private fun viewInfo() {
        // 회원 정보 API 연결 -> 정보 가져오기
    }
}