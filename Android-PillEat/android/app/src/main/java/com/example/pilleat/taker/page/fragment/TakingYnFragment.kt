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
        doTaking()

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

        return binding.root
    }

    private fun updateDate() {
        val current: String = SimpleDateFormat(format_MMdd, Locale.getDefault()).format(calendar.time)
        binding.takingynDateTv.text = current
    }

    private fun doTaking() {
        // 복용완료 api 불러와서 연결하기
        binding.takingynNoBtn.setOnClickListener {
            binding.takingynNoBtn.visibility = View.GONE
            binding.takingynYesBtn.visibility = View.VISIBLE
        }
        binding.takingynYesBtn.setOnClickListener {
            binding.takingynNoBtn.visibility = View.VISIBLE
            binding.takingynYesBtn.visibility = View.GONE
        }

        binding.takingynNo2Btn.setOnClickListener {
            binding.takingynNo2Btn.visibility = View.GONE
            binding.takingynYes2Btn.visibility = View.VISIBLE
        }
        binding.takingynYes2Btn.setOnClickListener {
            binding.takingynNo2Btn.visibility = View.VISIBLE
            binding.takingynYes2Btn.visibility = View.GONE
        }

        binding.takingynNo3Btn.setOnClickListener {
            binding.takingynNo3Btn.visibility = View.GONE
            binding.takingynYes3Btn.visibility = View.VISIBLE
        }
        binding.takingynYes3Btn.setOnClickListener {
            binding.takingynNo3Btn.visibility = View.VISIBLE
            binding.takingynYes3Btn.visibility = View.GONE
        }

        binding.takingynNo4Btn.setOnClickListener {
            binding.takingynNo4Btn.visibility = View.GONE
            binding.takingynYes4Btn.visibility = View.VISIBLE
        }
        binding.takingynYes4Btn.setOnClickListener {
            binding.takingynNo4Btn.visibility = View.VISIBLE
            binding.takingynYes4Btn.visibility = View.GONE
        }

        binding.takingynNo5Btn.setOnClickListener {
            binding.takingynNo5Btn.visibility = View.GONE
            binding.takingynYes5Btn.visibility = View.VISIBLE
        }
        binding.takingynYes5Btn.setOnClickListener {
            binding.takingynNo5Btn.visibility = View.VISIBLE
            binding.takingynYes5Btn.visibility = View.GONE
        }

        binding.takingynNo6Btn.setOnClickListener {
            binding.takingynNo6Btn.visibility = View.GONE
            binding.takingynYes6Btn.visibility = View.VISIBLE
        }
        binding.takingynYes6Btn.setOnClickListener {
            binding.takingynNo6Btn.visibility = View.VISIBLE
            binding.takingynYes6Btn.visibility = View.GONE
        }

        binding.takingynNo7Btn.setOnClickListener {
            binding.takingynNo7Btn.visibility = View.GONE
            binding.takingynYes7Btn.visibility = View.VISIBLE
        }
        binding.takingynYes7Btn.setOnClickListener {
            binding.takingynNo7Btn.visibility = View.VISIBLE
            binding.takingynYes7Btn.visibility = View.GONE
        }

        binding.takingynNo8Btn.setOnClickListener {
            binding.takingynNo8Btn.visibility = View.GONE
            binding.takingynYes8Btn.visibility = View.VISIBLE
        }
        binding.takingynYes8Btn.setOnClickListener {
            binding.takingynNo8Btn.visibility = View.VISIBLE
            binding.takingynYes8Btn.visibility = View.GONE
        }
    }
}