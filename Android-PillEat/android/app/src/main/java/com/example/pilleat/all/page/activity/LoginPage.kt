package com.example.pilleat.all.page.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.pilleat.WebSocketManager
import com.example.pilleat.all.response.LoginResponse
import com.example.pilleat.all.response.Result
import com.example.pilleat.all.service.AuthService
import com.example.pilleat.all.table.Auth
import com.example.pilleat.all.view.LoginView
import com.example.pilleat.databinding.ActivityLoginBinding
import com.example.pilleat.manager.page.activity.MainManagerPage
import com.example.pilleat.protector.page.activity.MainProtectorPage
import com.example.pilleat.taker.page.activity.MainTakerPage
import org.json.JSONObject
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

    private fun createJsonData(result: Result): JSONObject {
        val jsonData = JSONObject()
        jsonData.put("type", "login") // 원하는 키-값 쌍을 추가할 수 있습니다.
        jsonData.put("clientId", result.userId)
        Log.d("LoginPage-socket", result.toString())
        return jsonData
    }

    // 회원가입 페이지로 이동
    private fun joinPage() {
        val intent = Intent(this@LoginPage, JoinPage::class.java)
        startActivity(intent)
    }

    // 로그인 이후의 페이지(홈페이지)로 이동
    private fun nextActivity(result: Result) {
        if(result.type == "taker") {
            takerPage(result)
        } else if(result.type == "protector") {
            protectorPage(result)
        } else {
            managerPage()
        }
    }
    private fun takerPage(takerResult: Result) {
        val intent = Intent(this@LoginPage, MainTakerPage::class.java)
        intent.putExtra("takerId", takerResult.userId)
        intent.putExtra("takerType", takerResult.type)
        startActivity(intent)
    }

    private fun protectorPage(protectorResult: Result) {
        val intent = Intent(this@LoginPage, MainProtectorPage::class.java)
        intent.putExtra("protectorId", protectorResult.userId)
        intent.putExtra("getTakerId", protectorResult.takerId)
        intent.putExtra("protectorType", protectorResult.type)
        startActivity(intent)
    }

    private fun managerPage() {
        val intent = Intent(this@LoginPage, MainManagerPage::class.java)
        startActivity(intent)
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
        val auth = Auth(email, pwd)

        val authService = AuthService()
        authService.setLoginView(this@LoginPage)
        authService.login(auth)
    }

    override fun onLoginSuccess(code: Int, result: Result) {
        when(code) {
            1000 -> {
                val webSocket = WebSocketManager.getInstance(this).getWebSocket()

                // JSON 데이터 생성
                val jsonData = createJsonData(result)

                // WebSocket을 통해 JSON 데이터 전송
                webSocket.send(jsonData.toString())
                nextActivity(result)
                Log.d("LOGIN-SUCCESS", result.toString())
                Toast.makeText(this@LoginPage, "로그인되었습니다.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onLoginFailure(response: Response<LoginResponse>) {
        val resp: LoginResponse = response.body()!!
        Toast.makeText(this@LoginPage, resp.message, Toast.LENGTH_SHORT).show()
    }
}