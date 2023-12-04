package com.example.pilleat.all.page.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.pilleat.R
import com.example.pilleat.databinding.ActivityLoginBinding

class LoginPage: AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.loginBtn.setOnClickListener {
            login()
        }

        binding.loginFindAccountTv.setOnClickListener {
            findAccountPage()
        }

        binding.loginJoinTv.setOnClickListener {
            joinPage()
        }
    }

    // 이메일/비밀번호찾기 페이지로 이동
    private fun findAccountPage() {
        val intent = Intent(this@LoginPage, FindAccountPage::class.java)
        startActivity(intent)
    }

    // 회원가입 페이지로 이동
    private fun joinPage() {
        val intent = Intent(this@LoginPage, JoinPage::class.java)
        startActivity(intent)
    }

    private fun login() {
        // 이메일, 비밀번호 입력창 중, 입력되지 않은 부분이 있다면 토스트 메세지
        if(binding.loginInputEmailEt.text.toString().isEmpty() || binding.loginInputPwEt.text.toString().isEmpty()) {
            Toast.makeText(this@LoginPage, "모두 입력해주세요.", Toast.LENGTH_SHORT).show()
            return
        }

        // 관리자 계정으로 로그인 시,

        // 복용자 계정으로 로그인 시,

        // 보호자 계정으로 로그인 시,
    }
}