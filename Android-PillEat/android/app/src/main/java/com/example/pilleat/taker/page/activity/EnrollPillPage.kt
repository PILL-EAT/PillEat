package com.example.pilleat.taker.page.activity

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.pilleat.all.response.UserResult
import com.example.pilleat.databinding.ActivityEnrollBinding
import com.example.pilleat.taker.response.EnrollPillResponse
import com.example.pilleat.taker.service.EnrollPillService
import com.example.pilleat.taker.table.EnrollPill
import com.example.pilleat.taker.view.EnrollPillView
import com.google.gson.Gson
import retrofit2.Response

class EnrollPillPage: AppCompatActivity(), EnrollPillView {
    private lateinit var binding: ActivityEnrollBinding
    private lateinit var userResult: UserResult

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEnrollBinding.inflate(layoutInflater)
        setContentView(binding.root)

        userResult = getUserData()!!

        binding.enrollTimeBtn.setOnClickListener {
            enroll()
            binding.enrollBtn.isEnabled = true
        }

        binding.enrollBtn.setOnClickListener {
            connectEnroll(enrollPill(), userResult.id)
        }
    }

    private fun getUserData(): UserResult? {
        val spf = getSharedPreferences("userData", MODE_PRIVATE)
        val userJson = spf.getString("userData", null)

        return if (userJson != null) {
            val gson = Gson()
            gson.fromJson(userJson, UserResult::class.java)
        } else {
            null
        }
    }

    private fun enroll() {
        if (binding.enrollInputNameEt.text.toString().isEmpty() ||
            binding.enrollInputCategoryEt.text.toString().isEmpty() ||
            binding.enrollInputDateEt.text.toString().isEmpty()) {
            Toast.makeText(this@EnrollPillPage, "모두 입력해주세요.", Toast.LENGTH_SHORT).show()
        }

        if (binding.enrollInputDateEt.text.toString() == "1") {
            binding.enrollTime1Et.visibility = View.VISIBLE
            if(binding.enrollTime1Et.text.toString().isEmpty()) {
                Toast.makeText(this@EnrollPillPage, "모두 입력해주세요.", Toast.LENGTH_SHORT).show()
                return
            }
        } else if (binding.enrollInputDateEt.text.toString() == "2") {
            binding.enrollTime1Et.visibility = View.VISIBLE
            binding.enrollTime2Et.visibility = View.VISIBLE
            if(binding.enrollTime1Et.text.toString().isEmpty() || binding.enrollTime2Et.text.toString().isEmpty()) {
                Toast.makeText(this@EnrollPillPage, "모두 입력해주세요.", Toast.LENGTH_SHORT).show()
                return
            }
        } else if (binding.enrollInputDateEt.text.toString() == "3") {
            binding.enrollTime1Et.visibility = View.VISIBLE
            binding.enrollTime2Et.visibility = View.VISIBLE
            binding.enrollTime3Et.visibility = View.VISIBLE
            if(binding.enrollTime1Et.text.toString().isEmpty() || binding.enrollTime2Et.text.toString().isEmpty() || binding.enrollTime3Et.text.toString().isEmpty()) {
                Toast.makeText(this@EnrollPillPage, "모두 입력해주세요.", Toast.LENGTH_SHORT).show()
                return
            }
        }
    }

    private fun connectEnroll(enrollPill: EnrollPill, userId: Int) {
        val enrollPillService = EnrollPillService()
        enrollPillService.setEnrollPillView(this@EnrollPillPage)

        enrollPillService.enrollPill(enrollPill, userId)
    }

    private fun enrollPill(): EnrollPill {
        val pill_name: String = binding.enrollInputNameEt.text.toString()
        val pill_category: String = binding.enrollInputCategoryEt.text.toString()
        val pill_date: Int = binding.enrollInputDateEt.text.toString().toInt()
        val pill_time1: String = binding.enrollTime1Et.text.toString()
        val pill_time2: String = binding.enrollTime2Et.text.toString()
        val pill_time3: String = binding.enrollTime3Et.text.toString()

        return EnrollPill(pill_name, pill_category, pill_date, pill_time1, pill_time2, pill_time3) // 사용자의 입력값 리턴
    }

    override fun onEnrollPillSuccess() {
        Toast.makeText(this@EnrollPillPage, "등록되었습니다.", Toast.LENGTH_SHORT).show()
        finish()
    }

    override fun onEnrollPillFailure(response: Response<EnrollPillResponse>) {
        Toast.makeText(this@EnrollPillPage, response.message(), Toast.LENGTH_SHORT).show()
    }
}