package com.example.pilleat.taker.page.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pilleat.databinding.FragmentEnrolllistBinding
import com.example.pilleat.taker.response.EnrollPillListResponse
import com.example.pilleat.taker.response.EnrollPillResponse
import com.example.pilleat.taker.response.PillList
import com.example.pilleat.taker.rvadapter.EnrollListRVAdapter
import com.example.pilleat.taker.service.EnrollPillListService
import com.example.pilleat.taker.table.EnrollPill
import com.example.pilleat.taker.view.EnrollPillDelView
import com.example.pilleat.taker.view.EnrollPillListView

class EnrollPillListFragment: Fragment(), EnrollPillListView, EnrollPillDelView {
    private lateinit var binding: FragmentEnrolllistBinding
    private lateinit var enrollPillListRVAdapter: EnrollListRVAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentEnrolllistBinding.inflate(inflater, container, false)

        initRecyclerView()
        getData()
        return binding.root
    }

    private fun getUserId(): Int {
        val userId = arguments?.getInt("userId", 0)!!
        Log.d("LIST", userId.toString())
        return userId
    }

    private fun getUserId2(): Int {
        val userId = arguments?.getInt("userIdx", 0)!!
        Log.d("LIST", userId.toString())
        return userId
    }

    private fun getData() {
        val enrollPillListService = EnrollPillListService()
        enrollPillListService.setEnrollPillListView(this@EnrollPillListFragment)

        if(getUserId() == 0) {
            enrollPillListService.enrollPillList(getUserId2())
        } else {
            enrollPillListService.enrollPillList(getUserId())
        }
    }

    private fun getDrugId(drugId: Int): Int {
        return drugId
    }

    private fun deleteData(drugId: Int) {
        val enrollPillListService = EnrollPillListService()
        enrollPillListService.setEnrollPillDeleteView(this@EnrollPillListFragment)

        enrollPillListService.enrollPillDelete(drugId)
    }

    private fun initRecyclerView() {
        enrollPillListRVAdapter = EnrollListRVAdapter()
        binding.enrollListRv.adapter = enrollPillListRVAdapter
        binding.enrollListRv.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        binding.enrollListRv.setHasFixedSize(true)

        enrollPillListRVAdapter.setMyItemClickListener(object : EnrollListRVAdapter.MyItemClickListener {
            override fun onItemRemove(result: PillList) {
                deleteData(getDrugId(result.drugId))
                Toast.makeText(requireContext(), "삭제되었습니다.", Toast.LENGTH_SHORT).show()
            }
        })
    }

    override fun onEnrollPillListSuccess(code: Int, result: EnrollPillListResponse) {
        initRecyclerView()
        enrollPillListRVAdapter.setData(result.result.drugs)
        Toast.makeText(requireContext(), "불러오기 성공", Toast.LENGTH_SHORT).show()
    }

    override fun onEnrollPillListFailure(code: Int, message: String) {
        Toast.makeText(requireContext(), "불러오기 실패", Toast.LENGTH_SHORT).show()
    }

    override fun onDeleteSuccess(code: Int, response: EnrollPillResponse) {
        Log.d("삭제 완료", response.message)
    }

    override fun onDeleteFailure(code: Int, message: String) {
        Log.d("삭제 실패", message)
    }
}