package com.example.pilleat.all.page.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.pilleat.databinding.ActivityJoinBinding

class JoinPage: AppCompatActivity() {
    private lateinit var binding: ActivityJoinBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityJoinBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // api연결해서 code == 200일 때 회원가입 성공
        binding.joinBtn.setOnClickListener {
            join()
        }
    }

    // 입력창이 모두 입력되지 않았을 때 토스트 메세지 출력
    private fun join() {
        if(binding.joinInputEmailEt.text.toString().isEmpty() || binding.joinInputPwEt.text.toString().isEmpty() || binding.joinInputBirthEt.text.toString().isEmpty() || binding.joinInputNameEt.text.toString().isEmpty() || binding.joinInputPhoneEt.text.toString().isEmpty() || binding.joinInputPwCheckEt.text.toString().isEmpty()) {
            Toast.makeText(this@JoinPage, "모두 입력해주세요.", Toast.LENGTH_SHORT).show()
            return
        }

        // 회원가입 API 연결

        //  로그인 페이지로 이동
        val intent = Intent(this@JoinPage, LoginPage::class.java)
        startActivity(intent)
    }
}