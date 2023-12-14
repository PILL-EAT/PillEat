package com.example.pilleat.taker.page.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.pilleat.all.ForeGround
import com.example.pilleat.databinding.ActivityEnrollBinding
import com.example.pilleat.taker.response.EnrollPillResponse
import com.example.pilleat.taker.service.EnrollPillService
import com.example.pilleat.taker.table.EnrollPill
import com.example.pilleat.taker.table.Times
import com.example.pilleat.taker.view.EnrollPillView
import retrofit2.Response
import java.util.Collections

class EnrollPillPage: AppCompatActivity(), EnrollPillView{
    private lateinit var binding: ActivityEnrollBinding
    private var mon: String = "0"
    private var tue: String = "0"
    private var wed: String = "0"
    private var thu: String = "0"
    private var fri: String = "0"
    private var sat: String = "0"
    private var sun: String = "0"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEnrollBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.enrollTimeBtn.setOnClickListener {
            enroll()
        }

        binding.enrollWeekBtn.setOnClickListener {
            showWeek()
            binding.enrollBtn.isEnabled = true
        }

        binding.enrollBtn.setOnClickListener {
            connectEnroll(enrollPill())
            goMain()
        }
    }

    private fun getData(): Int {
        val getIntent = intent
        val getData = getIntent.getIntExtra("takerId", 0)
        Log.d("약 등록 userId", getData.toString())
        return getData
    }

    private fun sendIoTYN() {
        val intent = Intent(this@EnrollPillPage, ForeGround::class.java)
        intent.putExtra("iotYN", enrollPill().iot)
        startActivity(intent)
    }

    private fun goMain() {
        val intent = Intent(this@EnrollPillPage, MainTakerPage::class.java)
        intent.putExtra("userIdx", getData())
        startActivity(intent)
    }

    private fun enroll() {
        if (binding.enrollInputNameEt.text.toString().isEmpty() ||
            binding.enrollInputCategoryEt.text.toString().isEmpty() ||
            binding.enrollInputDateEt.text.toString().isEmpty()) {
            Toast.makeText(this@EnrollPillPage, "모두 입력해주세요.", Toast.LENGTH_SHORT).show()
        }

        if (binding.enrollInputDateEt.text.toString() == "1") {
            binding.enrollTime1Lo.visibility = View.VISIBLE
            return
        } else if (binding.enrollInputDateEt.text.toString() == "2") {
            binding.enrollTime1Lo.visibility = View.VISIBLE
            binding.enrollTime2Lo.visibility = View.VISIBLE
        } else if (binding.enrollInputDateEt.text.toString() == "3") {
            binding.enrollTime1Lo.visibility = View.VISIBLE
            binding.enrollTime2Lo.visibility = View.VISIBLE
            binding.enrollTime3Lo.visibility = View.VISIBLE
        } else {
            binding.enrollTime1Lo.visibility = View.VISIBLE
            binding.enrollTime2Lo.visibility = View.VISIBLE
            binding.enrollTime3Lo.visibility = View.VISIBLE
            Toast.makeText(this@EnrollPillPage, "약은 하루에 최대 3번 복용하실 수 있습니다.", Toast.LENGTH_SHORT).show()
        }
    }

    private fun connectEnroll(enrollPill: EnrollPill) {
        val enrollPillService = EnrollPillService()
        enrollPillService.setEnrollPillView(this@EnrollPillPage)

        enrollPillService.enrollPill(enrollPill, getData())
    }

    private fun enrollPill(): EnrollPill {
        val takerId: Int = getData()
        val pill_name: String = binding.enrollInputNameEt.text.toString()
        val pill_category: String = binding.enrollInputCategoryEt.text.toString()
        val pill_date: Int = binding.enrollInputDateEt.text.toString().toInt()
        val pill_time1: String = binding.enrollTime1HourTv.text.toString() + ":" + binding.enrollTime1MinuteTv.text.toString()
        val pill_time2: String = binding.enrollTime2HourTv.text.toString() + ":" + binding.enrollTime2MinuteTv.text.toString()
        val pill_time3: String = binding.enrollTime3HourTv.text.toString() + ":" + binding.enrollTime3MinuteTv.text.toString()
        val times = Times(
            time1 = pill_time1,
            time2 = pill_time2,
            time3 = pill_time3
        )

        val pill_time : ArrayList<Times> = arrayListOf(times)
        val pill_day: String = showWeek()
        val pill_iot = if (binding.enrollIotCb.isChecked) 1 else 0

        return EnrollPill(takerId, pill_name, pill_category, pill_date, pill_time, pill_day, pill_iot) // 사용자의 입력값 리턴
    }

    private fun showWeek(): String {
        binding.weekLo.visibility = View.VISIBLE

        if(binding.weekMon.isChecked == true){
            mon = "1"
        }

        if(binding.weekTue.isChecked == true) {
            tue = "1"
        }

        if(binding.weekWed.isChecked == true) {
            wed = "1"
        }

        if(binding.weekThu.isChecked == true) {
            thu = "1"
        }

        if(binding.weekFri.isChecked == true) {
            fri = "1"
        }

        if(binding.weekSat.isChecked == true) {
            sat = "1"
        }

        if(binding.weekSun.isChecked == true) {
            sun = "1"
        }

        return mon + tue + wed + thu + fri + sat + sun
    }

    override fun onEnrollPillSuccess() {
        Toast.makeText(this@EnrollPillPage, "등록되었습니다.", Toast.LENGTH_SHORT).show()
    }

    override fun onEnrollPillFailure(response: Response<EnrollPillResponse>) {
        Toast.makeText(this@EnrollPillPage, response.message(), Toast.LENGTH_SHORT).show()
    }
}