package com.example.pilleat.protector.page.activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.pilleat.all.page.activity.SettingPage
import com.example.pilleat.databinding.ActivityMainprotectorBinding
import com.example.pilleat.taker.page.activity.HomeTakerPage

class MainProtectorPage: AppCompatActivity() {
    private lateinit var binding: ActivityMainprotectorBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainprotectorBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.mainProtectorHomeBtn.setOnClickListener {
            val intent = Intent(this@MainProtectorPage, HomeProtectorPage::class.java)
            startActivity(intent)
        }

        binding.mainProtectorSettingBtn.setOnClickListener {
            val intent = Intent(this@MainProtectorPage, SettingPage::class.java)
            startActivity(intent)
        }
    }
}