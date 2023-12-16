package com.example.pilleat.taker.page.activity

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.example.pilleat.R
import com.example.pilleat.all.page.activity.SearchPage
import com.example.pilleat.all.page.activity.SettingPage
import com.example.pilleat.databinding.ActivityMaintakerBinding
import com.example.pilleat.taker.page.dialog.SetTakerDialog
import com.example.pilleat.protector.service.SetTakerService
import com.example.pilleat.protector.view.SetTakerView
import com.example.pilleat.taker.service.EnrollDeviceService
import com.example.pilleat.taker.table.EnrollDevice
import com.example.pilleat.taker.table.PhoneNumber
import com.example.pilleat.taker.view.EnrollDeviceView


class MainTakerPage : AppCompatActivity(), SetTakerView, SetTakerDialog.SetTakerDialogListener, EnrollDeviceView {
    private lateinit var binding: ActivityMaintakerBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMaintakerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.mainTakerConnectBtn.setOnClickListener {
            // 다이얼로그 띄우기
            showSetIotDialog()
        }

        binding.mainTakerEnrollBtn.setOnClickListener {
            sendData()
        }

        binding.mainTakerSearchBtn.setOnClickListener {
            val intent = Intent(this@MainTakerPage, SearchPage::class.java)
            startActivity(intent)
        }

        binding.mainTakerHomeBtn.setOnClickListener {
            sendData2()
        }

        binding.mainTakerSettingBtn.setOnClickListener {
            sendData3()
        }

        binding.mainTakerEnrollProtectorBtn.setOnClickListener {
            showSetProtectorDialog()
        }
    }

    private fun setTaker(phone: PhoneNumber, takerId: Int) {
        val setTakerService = SetTakerService()
        setTakerService.initSetTakerView(this@MainTakerPage)
        setTakerService.inputTaker(phone, takerId)
    }

    private fun setIotDevice(enrollDevice: EnrollDevice, takerId: Int) {
        val setEnrollDeviceService = EnrollDeviceService()
        setEnrollDeviceService.setEnrollDeviceView(this@MainTakerPage)
        setEnrollDeviceService.enrollDevice(enrollDevice, takerId)
    }

    private fun showSetProtectorDialog() {
        val inflater = getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view = inflater.inflate(R.layout.dialog_enroll_taker, null)

        val alertDialog = AlertDialog.Builder(this@MainTakerPage)
            .setPositiveButton("등록/재등록") { dialog, which ->
                val inputText = view.findViewById<EditText>(R.id.dialog_enroll_taker_et)
                val userInput = inputText.text.toString()
                val phone = PhoneNumber(userInput)
                Log.d("text", userInput)
                if(getData() == 0) {
                    setTaker(phone, getData2())
                } else {
                    setTaker(phone, getData())
                }
            }
            .setNeutralButton("취소", null)
            .create()

        alertDialog.setView(view)
        alertDialog.show()
    }

    @SuppressLint("MissingInflatedId")
    private fun showSetIotDialog() {
        val inflater = getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view = inflater.inflate(R.layout.activity_maintaker_dialog, null)

        val alertDialog = AlertDialog.Builder(this@MainTakerPage)
            .setPositiveButton("등록/재등록") { dialog, which ->
                val inputText = view.findViewById<EditText>(R.id.dialog_enroll_iot_et)
                val userInput = inputText.text.toString()
                val iotCode = EnrollDevice(userInput)
                Log.d("IOT-CODE", iotCode.toString())
                if(getData() == 0) {
                    setIotDevice(iotCode, getData2())
                } else {
                    setIotDevice(iotCode, getData())
                }
            }
            .setNeutralButton("취소", null)
            .create()

        alertDialog.setView(view)
        alertDialog.show()
    }

    private fun getData(): Int {
        val getIntent = intent
        val getData = getIntent.getIntExtra("takerId", 0)
        return getData
    }

    private fun getData2(): Int {
        val getIntent = intent
        val getData = getIntent.getIntExtra("userIdx", 0)
        return getData
    }

    private fun getData3(): Int {
        val getIntent = intent
        val getData = getIntent.getIntExtra("clientId", 0)
        return getData
    }

    private fun sendData() {
        val intent = Intent(this@MainTakerPage, EnrollPillPage::class.java)
        intent.putExtra("takerId", getData())
        startActivity(intent)
    }

    private fun sendData2() {
        val intent = Intent(this@MainTakerPage, HomeTakerPage::class.java)
        intent.putExtra("userId", getData())
        intent.putExtra("userIdx", getData2())
        intent.putExtra("clientId", getData3())
        startActivity(intent)
    }

    private fun sendData3() {
        val intent = Intent(this@MainTakerPage, SettingPage::class.java)
        intent.putExtra("takerId", getData())
        startActivity(intent)
    }

    override fun onSetTakerSuccess(code: Int) {
//        Toast.makeText(this@MainTakerPage, "등록되었습니다.", Toast.LENGTH_SHORT).show()
        Log.d("보호자 등록", code.toString())
    }

    override fun onSetTakerFailure(code: Int, message: String) {
        Toast.makeText(this@MainTakerPage, message, Toast.LENGTH_SHORT).show()
    }

    override fun onPhoneSet(phone: String) {
        Log.d("phone", "reset")
    }

    override fun onSetIotDeviceSuccess(code: Int) {
        Log.d("IOT-CONNECT-SUCCESS", code.toString())
        Toast.makeText(this@MainTakerPage, "기기가 등록되었습니다.", Toast.LENGTH_SHORT).show()
    }

    override fun onSetIotDeviceFailure(code: Int, message: String) {
        Log.d("IOT-CONNECT-FAILURE", message)
        Toast.makeText(this@MainTakerPage, message, Toast.LENGTH_SHORT).show()
    }
}