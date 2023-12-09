package com.example.pilleat.all.page.activity

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pilleat.all.response.AllPillInfoResponse
import com.example.pilleat.all.response.ItemResult
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

        getData(itemName = "")

        binding.searchBtn.setOnClickListener {
            val itemName = binding.searchInputSearchEt.text.toString()
            if (itemName.isNotEmpty()) {
                getData(itemName)
            } else {
                notInput()
            }
        }
    }

    private fun notInput() {
        if(binding.searchInputSearchEt.text.toString().isEmpty()) {
            Toast.makeText(this@SearchPage, "검색어를 입력하세요.", Toast.LENGTH_SHORT).show()
        }
    }

    private fun initRecyclerView(result: AllPillInfoResponse) {
        allPillInfoRVAdapter = AllPillInfoRVAdapter(this@SearchPage, result)
        binding.searchResultRv.adapter = allPillInfoRVAdapter
        binding.searchResultRv.setHasFixedSize(true)
        binding.searchResultRv.layoutManager = LinearLayoutManager(this@SearchPage, LinearLayoutManager.VERTICAL, false)
        allPillInfoRVAdapter.notifyDataSetChanged()
    }

    private fun getData(itemName: String) {
        val allPillInfoService = AllPillInfoService()
        allPillInfoService.setAddPillInfoView(this@SearchPage)

        allPillInfoService.getData(itemName)
    }

    override fun onGetDataSuccess(result: AllPillInfoResponse) {
        initRecyclerView(result)
    }

    override fun onGetDataFailure(message: String) {
        Toast.makeText(this@SearchPage, "오류가 발생했습니다.", Toast.LENGTH_SHORT).show()
    }
}