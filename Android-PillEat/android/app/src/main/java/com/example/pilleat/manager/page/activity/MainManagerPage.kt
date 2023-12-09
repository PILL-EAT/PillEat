package com.example.pilleat.manager.page.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.pilleat.R
import com.example.pilleat.databinding.ActivityMainmanagerBinding

class MainManagerPage: AppCompatActivity() {
    private lateinit var binding: ActivityMainmanagerBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainmanagerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.mainManagerUserInfoBtn.setOnClickListener {
            val intent = Intent(this@MainManagerPage, UserInfoPage::class.java)
            startActivity(intent)
        }

        binding.mainManagerLogoutBtn.setOnClickListener {
            logout()
        }
    }

    private fun logout() {
        val inflater = getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view = inflater.inflate(R.layout.dialog_logout, null)

        val alertDialog = AlertDialog.Builder(this@MainManagerPage)
            .setPositiveButton("예") { dialog, which ->
                // 임시 설정 -> 로그아웃 API 연결
                Toast.makeText(this@MainManagerPage, "로그아웃되었습니다.", Toast.LENGTH_SHORT).show()
                finish()
            }
            .setNeutralButton("아니오", null)
            .create()
        alertDialog.setView(view)
        alertDialog.show()
    }
}