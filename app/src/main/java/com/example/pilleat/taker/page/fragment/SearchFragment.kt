package com.example.pilleat.taker.page.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.pilleat.databinding.ActivitySearchBinding

class SearchFragment: Fragment() {
    private lateinit var binding: ActivitySearchBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = ActivitySearchBinding.inflate(inflater, container, false)

        binding.searchBtn.setOnClickListener {
            showResult()
        }

        return binding.root
    }

    // 검색 버튼을 눌렀을 때 -> api연결해서 결과 보여주기
    private fun showResult() {
        notInput()
        // binding.searchInputSearchEt.text와 약 db에 있는 약 이름과 비교해서
        // binding.searchResultNameTv.text, binding.searchResultContentsTv.text 값 변경하기
    }

    // 입력하지 않았을 때, 토스트 메세지 출력
    private fun notInput() {
        if(binding.searchInputSearchEt.text.toString().isEmpty()) {
            Toast.makeText(requireContext(), "검색어를 입력하세요.", Toast.LENGTH_SHORT).show()
        }
    }
}