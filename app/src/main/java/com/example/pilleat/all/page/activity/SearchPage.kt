package com.example.pilleat.all.page.activity

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.pilleat.databinding.ActivitySearchBinding

class SearchPage: AppCompatActivity() {
    private lateinit var binding: ActivitySearchBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.searchBtn.setOnClickListener {
            showResult()
        }
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
            Toast.makeText(this@SearchPage, "검색어를 입력하세요.", Toast.LENGTH_SHORT).show()
        }
    }
}