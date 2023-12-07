package com.example.pilleat.all.page.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.pilleat.all.response.AuthResponse
import com.example.pilleat.all.response.Result
import com.example.pilleat.all.service.AuthService
import com.example.pilleat.all.table.User
import com.example.pilleat.all.view.LoginView
import com.example.pilleat.databinding.ActivityLoginBinding
import com.example.pilleat.taker.page.activity.HomeTakerPage
import retrofit2.Response

class LoginPage : AppCompatActivity(), LoginView {
    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.loginBtn.setOnClickListener {
            login()
        }

        binding.loginJoinTv.setOnClickListener {
            joinPage()
        }
    }

    // 회원가입 페이지로 이동
    private fun joinPage() {
        val intent = Intent(this@LoginPage, JoinPage::class.java)
        startActivity(intent)
    }

    // 로그인 이후의 페이지(홈페이지)로 이동
    private fun homePage() {
        val intent = Intent(this@LoginPage, HomeTakerPage::class.java)
        startActivity(intent)
    }

    // jwt 저장 함수 -> api용
    private fun saveJwt(jwt: String) {
        val spf = getSharedPreferences("auth", MODE_PRIVATE)
        val editor = spf.edit()

        editor.putString("jwt", jwt) // 어떤 키 값으로 저장할 지
        editor.apply()
        Log.d("LoginPage-JWT", jwt)
    }

    private fun login() {
        // 이메일, 비밀번호 입력창 중, 입력되지 않은 부분이 있다면 토스트 메세지
        if (binding.loginInputEmailEt.text.toString().isEmpty() || binding.loginInputPwEt.text.toString().isEmpty()
        ) {
            Toast.makeText(this@LoginPage, "모두 입력해주세요.", Toast.LENGTH_SHORT).show()
            return
        }

        val email: String = binding.loginInputEmailEt.text.toString()
        val pwd: String = binding.loginInputPwEt.text.toString()

        val authService = AuthService()
        authService.setLoginView(this@LoginPage)
        authService.login(User(email, pwd, "", "", "", "", ""))

        // 관리자 계정으로 로그인 시,

        // 복용자 계정으로 로그인 시,

        // 보호자 계정으로 로그인 시,
    }

    override fun onLoginSuccess(code: Int, result: Result) {
        when(code) {
            1000 -> {
                homePage()
            }
        }
        Toast.makeText(this@LoginPage, "로그인되었습니다.", Toast.LENGTH_SHORT).show()
    }

    override fun onLoginFailure(response: Response<AuthResponse>) {
        val resp: AuthResponse = response.body()!!
        Toast.makeText(this@LoginPage, resp.message, Toast.LENGTH_SHORT).show()
    }
}