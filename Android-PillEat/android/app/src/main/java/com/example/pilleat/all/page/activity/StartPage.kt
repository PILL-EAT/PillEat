package com.example.pilleat.all.page.activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.pilleat.databinding.ActivityStartBinding
import com.example.pilleat.manager.page.activity.MainManagerPage
import com.example.pilleat.protector.page.activity.MainProtectorPage
import com.example.pilleat.taker.page.activity.MainTakerPage

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

        binding.startChecking.setOnClickListener {
            val intent = Intent(this@StartPage, MainTakerPage::class.java)
            startActivity(intent)
        }

        binding.startChecking2.setOnClickListener {
            val intent = Intent(this@StartPage, MainProtectorPage::class.java)
            startActivity(intent)
        }

        binding.startChecking3.setOnClickListener {
            val intent = Intent(this@StartPage, MainManagerPage::class.java)
            startActivity(intent)
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