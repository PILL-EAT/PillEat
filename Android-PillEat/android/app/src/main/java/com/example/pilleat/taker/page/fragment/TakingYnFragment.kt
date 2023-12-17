package com.example.pilleat.taker.page.fragment

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pilleat.R
import com.example.pilleat.WebSocketManager
import com.example.pilleat.all.response.Result
import com.example.pilleat.databinding.FragmentTakingynBinding
import com.example.pilleat.taker.response.EnrollRecordResponse
import com.example.pilleat.taker.response.PillList
import com.example.pilleat.taker.response.RecordResult
import com.example.pilleat.taker.rvadapter.EnrollListRVAdapter
import com.example.pilleat.taker.rvadapter.TakingYnRVAdapter
import com.example.pilleat.taker.service.EnrollPillListService
import com.example.pilleat.taker.service.EnrollRecordService
import com.example.pilleat.taker.view.EnrollRecordView
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class TakingYnFragment: Fragment(), EnrollRecordView {
    private lateinit var binding: FragmentTakingynBinding
    private var format_MMdd = "MM월 dd일"
    private var format_yyyyMMdd = "yyyy-MM-dd"
    private val calendar = Calendar.getInstance()
    private lateinit var takingYnRVAdapter: TakingYnRVAdapter

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
            getData()
        }

        binding.takingynNextBtn.setOnClickListener {
            // 내일 날짜로 이동
            calendar.add(Calendar.DAY_OF_YEAR, 1)
            updateDate()
            getData()
        }

        if(getTakerType() == "taker") {
            binding.takingynPullBtn.setOnClickListener {
                // 약 추출
                Log.d("USER", getUserId().toString())
                sendToServer(requireContext(), getUserId())
//                Toast.makeText(context, "약이 추출됩니다.", Toast.LENGTH_SHORT).show()
            }
        } else {
            binding.takingynPullBtn.isEnabled = false
        }

        getData()
        return binding.root
    }

    private fun getUserId(): Int {
        val userId = arguments?.getInt("userId", 0)
        return userId ?: 0
    }

    private fun getData() {
        val current: String = SimpleDateFormat(format_yyyyMMdd, Locale.getDefault()).format(calendar.time)
        val enrollRecordService = EnrollRecordService()
        enrollRecordService.setEnrollRecordView(this@TakingYnFragment)

        enrollRecordService.enrollRecord(getUserId(), current)
    }

    fun getTakerType(): String {
        val takerType = arguments?.getString("takerType")!!
        return takerType
    }

    private fun updateDate() {
        val current: String = SimpleDateFormat(format_MMdd, Locale.getDefault()).format(calendar.time)
        binding.takingynDateTv.text = current
    }

    private fun initRecyclerView(response: EnrollRecordResponse) {
        takingYnRVAdapter = TakingYnRVAdapter(this, response)
        binding.takingynRv.adapter = takingYnRVAdapter
        binding.takingynRv.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        binding.takingynRv.setHasFixedSize(true)

        takingYnRVAdapter.setMyItemClickListener(object : TakingYnRVAdapter.MyItemClickListener {
            override fun onItemClick(result: RecordResult) {
                Toast.makeText(context, "click", Toast.LENGTH_SHORT).show()
            }
        })

        binding.takingynRv.adapter = takingYnRVAdapter
    }

    private fun createJsonData(userId: Int): JSONObject {
        val jsonData = JSONObject()
        jsonData.put("type", "takePill") // 적절한 type을 설정
        jsonData.put("clientId", userId) // 클릭된 아이템의 id 또는 필요한 데이터를 전송
        return jsonData
    }

    private fun sendToServer(context: Context, userId: Int) {
        val webSocket = WebSocketManager.getInstance(context).getWebSocket()
        val jsonData = createJsonData(userId)
        webSocket.send(jsonData.toString())
    }

    override fun onGetRecordSuccess(code: Int, response: EnrollRecordResponse) {
        initRecyclerView(response)
        takingYnRVAdapter.setData(response.result.list)
    }

    override fun onGetRecordFailure(code: Int, message: String) {
        Log.d("RECORD-FAIL", message)
        Toast.makeText(context, "오류가 발생했습니다", Toast.LENGTH_SHORT).show()
    }
}