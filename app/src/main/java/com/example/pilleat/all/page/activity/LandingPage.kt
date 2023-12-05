package com.example.pilleat.all.page.activity

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.example.pilleat.databinding.ActivityLandingBinding

class LandingPage: AppCompatActivity() {
    private lateinit var binding: ActivityLandingBinding
    private var handler = Handler()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLandingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        handler.postDelayed({
            val intent = Intent(this@LandingPage, StartPage::class.java) // 임시설정 -> 자동로그인 시, MainTakerPage or MainProtectorPage로 이동
            startActivity(intent)
            finish()
        }, 1000)
    }
}