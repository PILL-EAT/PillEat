package com.example.pilleat.all.page.activity

import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.text.Layout
import android.view.LayoutInflater
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.pilleat.R
import com.example.pilleat.databinding.ActivitySettingBinding

class SettingPage: AppCompatActivity() {
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
            delete()
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

    private fun delete() {
        val inflater = getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view = inflater.inflate(R.layout.dialog_delete, null)

        val alertDialog = AlertDialog.Builder(this@SettingPage)
            .setPositiveButton("예") { dialog, which ->
                // 임시 설정 -> 회원탈퇴 API 연결
                Toast.makeText(this@SettingPage, "탈퇴되었습니다.", Toast.LENGTH_SHORT).show()
            }
            .setNeutralButton("아니오", null)
            .create()
        alertDialog.setView(view)
        alertDialog.show()
    }
}