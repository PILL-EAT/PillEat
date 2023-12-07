package com.example.pilleat.taker.page.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TimePicker
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.pilleat.all.page.activity.LoginPage
import com.example.pilleat.all.service.AuthService
import com.example.pilleat.all.table.User
import com.example.pilleat.databinding.ActivityEnrollBinding
import com.example.pilleat.taker.response.EnrollPillResponse
import com.example.pilleat.taker.service.EnrollPillService
import com.example.pilleat.taker.table.EnrollPill
import com.example.pilleat.taker.view.EnrollPillView
import retrofit2.Response

class EnrollPillPage: AppCompatActivity(), TimePicker.OnTimeChangedListener, EnrollPillView {
    private lateinit var binding: ActivityEnrollBinding
    private lateinit var mTimePicker: TimePicker
    private lateinit var user: User

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEnrollBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mTimePicker = binding.enrollTimeTp
        mTimePicker.setIs24HourView(true)
        mTimePicker.setOnTimeChangedListener(this@EnrollPillPage)

        binding.enrollInputTimeEt.inputType = 0 // 키보드 안뜨게 하기

        binding.enrollBtn.setOnClickListener {
            enroll()
        }

        // 등록할 시간 입력창 누르면 TimePicker 보임
        binding.enrollInputTimeEt.setOnClickListener {
            binding.enrollTimeTp.visibility = View.VISIBLE
        }

        // 배경 레이아웃 클릭 시, timepicker 내려감
        binding.enrollLo.setOnClickListener {
            binding.enrollTimeTp.visibility = View.GONE
        }
    }

    private fun enroll() {
        if(binding.enrollInputNameEt.text.toString().isEmpty() || binding.enrollInputCategoryEt.text.toString().isEmpty() || binding.enrollInputVolumnEt.text.toString().isEmpty() || binding.enrollInputTimeEt.text.toString().isEmpty()) {
            Toast.makeText(this@EnrollPillPage, "모두 입력해주세요.", Toast.LENGTH_SHORT).show()
            return
        } else {
            // 약 등록 API 연결 -> 임시설정
            Toast.makeText(this@EnrollPillPage, "등록되었습니다.", Toast.LENGTH_SHORT).show()
            val intent = Intent(this@EnrollPillPage, MainTakerPage::class.java)
            startActivity(intent)
        }

        val enrollPillService = EnrollPillService()
        enrollPillService.setEnrollPillView(this@EnrollPillPage)

        enrollPillService.enrollPill(enrollPill())
    }

    override fun onTimeChanged(timePicker: TimePicker?, hour: Int, minute: Int) {
        binding.enrollInputTimeEt.setText(hour.toString() + "시 " + minute.toString() + "분")
    }

    private fun enrollPill(): EnrollPill {
        val pill_name: String = binding.enrollInputNameEt.text.toString()
        val pill_time: String = binding.enrollInputTimeEt.text.toString()
        val pill_volumn: String = binding.enrollInputVolumnEt.text.toString()
        val pill_category: String = binding.enrollInputCategoryEt.text.toString()

        return EnrollPill(pill_name, pill_category, pill_time, pill_volumn) // 사용자의 입력값 리턴
    }

    override fun onEnrollPillSuccess() {
        Toast.makeText(this@EnrollPillPage, "등록되었습니다.", Toast.LENGTH_SHORT).show()
        finish()
    }

    override fun onEnrollPillFailure(response: Response<EnrollPillResponse>) {
        Toast.makeText(this@EnrollPillPage, response.message(), Toast.LENGTH_SHORT).show()
    }
}