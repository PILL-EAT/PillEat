package com.example.pilleat.taker.page.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.pilleat.databinding.FragmentTakingynBinding
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class TakingYnFragment: Fragment() {
    private lateinit var binding: FragmentTakingynBinding
    private var format_MMdd = "MM월 dd일"
    private val calendar = Calendar.getInstance()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTakingynBinding.inflate(inflater, container, false)
        updateDate()

        binding.takingynBeforeBtn.setOnClickListener {
            // 어제 날짜로 이동
            calendar.add(Calendar.DAY_OF_YEAR, -1)
            updateDate()
        }

        binding.takingynNextBtn.setOnClickListener {
            // 내일 날짜로 이동
            calendar.add(Calendar.DAY_OF_YEAR, 1)
            updateDate()
        }

        // 보호자 계정으로 로그인 시, 클릭 X
        binding.takingynNoBtn.setOnClickListener {
            doTaking()
        }

        binding.takingynYesBtn.setOnClickListener {
            doTaking()
        }

        return binding.root
    }

    private fun updateDate() {
        val current: String = SimpleDateFormat(format_MMdd, Locale.getDefault()).format(calendar.time)
        binding.takingynDateTv.text = current
    }

    private fun doTaking() {
        // 복용완료 api 불러와서 연결하기
        if(binding.takingynNoBtn.visibility == View.VISIBLE) {
            binding.takingynNoBtn.visibility = View.GONE
            binding.takingynYesBtn.visibility = View.VISIBLE
        } else {
            binding.takingynNoBtn.visibility = View.VISIBLE
            binding.takingynYesBtn.visibility = View.GONE
        }
    }
}