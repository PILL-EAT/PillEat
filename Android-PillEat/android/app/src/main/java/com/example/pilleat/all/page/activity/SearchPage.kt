package com.example.pilleat.all.page.activity

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pilleat.all.response.AllPillInfoResponse
import com.example.pilleat.all.rvadapter.AllPillInfoRVAdapter
import com.example.pilleat.all.service.AllPillInfoService
import com.example.pilleat.all.view.AllPillInfoView
import com.example.pilleat.databinding.ActivitySearchBinding

class SearchPage: AppCompatActivity(), AllPillInfoView {
    private lateinit var binding: ActivitySearchBinding
    private lateinit var allPillInfoRVAdapter: AllPillInfoRVAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //getData("요오드")

        binding.searchBtn.setOnClickListener {
            showResult()
        }
    }

    override fun onStart() {
        super.onStart()

        val essntl_item_name =  binding.searchInputSearchEt.text.toString()
//        getData(essntl_item_name)
        getData()
    }

    // 검색 버튼을 눌렀을 때 -> api연결해서 결과 보여주기
    private fun showResult() {
        notInput()
        val essntl_item_name =  binding.searchInputSearchEt.text.toString()
//        getData(essntl_item_name)
        getData()
    }

    // 입력하지 않았을 때, 토스트 메세지 출력
    private fun notInput() {
        if(binding.searchInputSearchEt.text.toString().isEmpty()) {
            Toast.makeText(this@SearchPage, "검색어를 입력하세요.", Toast.LENGTH_SHORT).show()
        }
    }

    private fun getData() {
        val allPillInfoService = AllPillInfoService()
        allPillInfoService.setAddPillInfoView(this@SearchPage)

//        allPillInfoService.getData(essntlItemName)
        allPillInfoService.getData()
    }

    private fun initRecyclerView(result: AllPillInfoResponse) {
        allPillInfoRVAdapter = AllPillInfoRVAdapter(this@SearchPage, result)
        binding.searchResultRv.adapter = allPillInfoRVAdapter
        binding.searchResultRv.layoutManager = LinearLayoutManager(this@SearchPage, LinearLayoutManager.VERTICAL, false)
    }

    override fun onGetDataSuccess(result: AllPillInfoResponse) {
        Log.d("이마트", result.toString())
        initRecyclerView(result)
    }

    override fun onGetDataFailure(message: String) {
        Toast.makeText(this@SearchPage, "오류가 발생했습니다.", Toast.LENGTH_SHORT).show()
        Log.d("이마크", message)
    }
}