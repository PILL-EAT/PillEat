package com.example.pilleat.all.page.activity

import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.example.pilleat.all.response.JoinResponse
import com.example.pilleat.all.service.AuthService
import com.example.pilleat.all.table.User
import com.example.pilleat.all.view.JoinView
import com.example.pilleat.databinding.ActivityJoinBinding
import com.google.gson.Gson
import retrofit2.Response
import java.time.LocalDate

class JoinPage: AppCompatActivity(), JoinView {
    private lateinit var binding: ActivityJoinBinding
    private lateinit var user: User

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityJoinBinding.inflate(layoutInflater)
        setContentView(binding.root)

        user = getUser()

        binding.joinBtn.setOnClickListener {
            join()
            saveUserData(getUser())
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun getUser(): User {
        val email = binding.joinInputEmailEt.text.toString()
        val password = binding.joinInputPwEt.text.toString()
        val name = binding.joinInputNameEt.text.toString()
        val birth = binding.joinInputBirthEt.text.toString()
        val phone = binding.joinInputPhoneEt.text.toString()
        val date: LocalDate = LocalDate.now()
        val mode = if (binding.joinModeCb.isChecked) {
            "protector"
        } else {
            "taker"
        }

        return User(email, password, name, birth, phone, mode, date.toString())
    }

    private fun saveUserData(user: User) {
        val spf = getSharedPreferences("userData", MODE_PRIVATE)
        val editor = spf.edit()

        val gson = Gson()
        val userJson = gson.toJson(user)

        editor.putString("userData", userJson) // 어떤 키 값으로 저장할 지
        editor.apply()
    }

    // 입력창이 모두 입력되지 않았을 때 토스트 메세지 출력
    @RequiresApi(Build.VERSION_CODES.O)
    private fun join() {
        if(binding.joinInputEmailEt.text.toString().isEmpty() ||
            binding.joinInputPwEt.text.toString().isEmpty() ||
            binding.joinInputBirthEt.text.toString().isEmpty() ||
            binding.joinInputNameEt.text.toString().isEmpty() ||
            binding.joinInputPhoneEt.text.toString().isEmpty()) {
            Toast.makeText(this@JoinPage, "모두 입력해주세요.", Toast.LENGTH_SHORT).show()
            return
        }

        // 회원가입 API 연결
        val authService = AuthService()
        authService.setJoinView(this@JoinPage)

        authService.join(getUser())
    }

    override fun onJoinSuccess() {
        finish()
        Toast.makeText(this@JoinPage, "회원가입 되었습니다.", Toast.LENGTH_SHORT).show()
    }

    override fun onJoinFailure(response: Response<JoinResponse>) {
        if(response.code() == 2016) {
            Toast.makeText(this@JoinPage, "이메일 형식을 확인해주세요.", Toast.LENGTH_SHORT).show()
        } else if(response.code() == 2000) {
            Toast.makeText(this@JoinPage, "입력값을 확인해주세요.", Toast.LENGTH_SHORT).show()
        } else if(response.code() == 2017) {
            Toast.makeText(this@JoinPage, "중복된 이메일입니다.", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this@JoinPage, "회원가입에 실패하였습니다.", Toast.LENGTH_SHORT).show()
        }
    }
}