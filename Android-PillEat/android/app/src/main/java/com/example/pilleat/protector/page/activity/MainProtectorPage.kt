package com.example.pilleat.protector.page.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.pilleat.R
import com.example.pilleat.all.page.activity.SearchPage
import com.example.pilleat.all.page.activity.SettingPage
import com.example.pilleat.databinding.ActivityMainprotectorBinding
import com.example.pilleat.protector.page.dialog.SetTakerDialog
import com.example.pilleat.protector.service.SetTakerService
import com.example.pilleat.protector.view.SetTakerView

class MainProtectorPage: AppCompatActivity(), SetTakerView, SetTakerDialog.SetTakerDialogListener {
    private lateinit var binding: ActivityMainprotectorBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainprotectorBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.mainProtectorHomeBtn.setOnClickListener {
            val intent = Intent(this@MainProtectorPage, HomeProtectorPage::class.java)
            intent.putExtra("protectorId", getData())
            startActivity(intent)
        }

        binding.mainProtectorEnrollTakerBtn.setOnClickListener {
            showSetTakerDialog()
        }

        binding.mainProtectorSearchBtn.setOnClickListener {
            val intent = Intent(this@MainProtectorPage, SearchPage::class.java)
            startActivity(intent)
        }

        binding.mainProtectorSettingBtn.setOnClickListener {
            val intent = Intent(this@MainProtectorPage, SettingPage::class.java)
            intent.putExtra("protectorId", getData())
            startActivity(intent)
        }
    }

    private fun getData(): Int {
        val getIntent = intent
        val getData = getIntent.getIntExtra("protectorId", 0)
        return getData
    }

    private fun setTaker(phone: String, protectorId: Int) {
        val setTakerService = SetTakerService()
        setTakerService.initSetTakerView(this@MainProtectorPage)
        setTakerService.inputTaker(phone, protectorId)
    }

    private fun showSetTakerDialog() {
        val setTakerDialog = SetTakerDialog(this)
        setTakerDialog.setListener(this)
        setTakerDialog.show()
    }

    private fun enrollTaker() {
        val inflater = getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view = inflater.inflate(R.layout.dialog_enroll_taker, null)

        val alertDialog = AlertDialog.Builder(this@MainProtectorPage)
            .setPositiveButton("등록") { dialog, which ->
                Toast.makeText(this@MainProtectorPage, "등록되었습니다.", Toast.LENGTH_SHORT).show()
                // 추후 api 연결
            }
            .setNeutralButton("취소", null)
            .create()

        alertDialog.setView(view)
        alertDialog.show()
    }

    override fun onSetTakerSuccess(code: Int) {
        Log.d("ENROLL-TAKER", code.toString())
    }

    override fun onSetTakerFailure(code: Int, message: String) {
        Log.d("ENROLL-TAKER-FAIL", message)
    }

    override fun onPhoneSet(phone: String) {
        setTaker(phone, getData())
        Log.d("Set-Taker-Dialog", "Phone set: $phone")
    }
}