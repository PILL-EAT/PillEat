package com.example.pilleat.protector.page.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.pilleat.R
import com.example.pilleat.all.page.activity.SearchPage
import com.example.pilleat.all.page.activity.SettingPage
import com.example.pilleat.databinding.ActivityMainprotectorBinding

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

        binding.mainProtectorEnrollTakerBtn.setOnClickListener {
            enrollTaker()
        }

        binding.mainProtectorSearchBtn.setOnClickListener {
            val intent = Intent(this@MainProtectorPage, SearchPage::class.java)
            startActivity(intent)
        }

        binding.mainProtectorSettingBtn.setOnClickListener {
            val intent = Intent(this@MainProtectorPage, SettingPage::class.java)
            startActivity(intent)
        }
    }

    private fun enrollTaker() {
        val inflater = getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view = inflater.inflate(R.layout.dialog_enroll_taker, null)

        val alertDialog = AlertDialog.Builder(this@MainProtectorPage)
            .setPositiveButton("등록") { dialog, which ->
                Toast.makeText(this@MainProtectorPage, "등록되었습니다.", Toast.LENGTH_SHORT).show()
                // 추후 api 연결
            }
            .setNeutralButton("취소", null)
            .create()

        alertDialog.setView(view)
        alertDialog.show()
    }
}