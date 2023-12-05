package com.example.pilleat.taker.page.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TimePicker
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.pilleat.databinding.ActivityEnrollBinding

class EnrollPage: AppCompatActivity(), TimePicker.OnTimeChangedListener {
    private lateinit var binding: ActivityEnrollBinding
    private lateinit var mTimePicker: TimePicker

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEnrollBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mTimePicker = binding.enrollTimeTp
        mTimePicker.setIs24HourView(true)
        mTimePicker.setOnTimeChangedListener(this@EnrollPage)

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
            Toast.makeText(this@EnrollPage, "모두 입력해주세요.", Toast.LENGTH_SHORT).show()
            return
        } else {
            // 약 등록 API 연결 -> 임시설정
            Toast.makeText(this@EnrollPage, "등록되었습니다.", Toast.LENGTH_SHORT).show()
            val intent = Intent(this@EnrollPage, MainTakerPage::class.java)
            startActivity(intent)
        }
    }

    override fun onTimeChanged(timePicker: TimePicker?, hour: Int, minute: Int) {
        binding.enrollInputTimeEt.setText(hour.toString() + "시 " + minute.toString() + "분")
    }
}