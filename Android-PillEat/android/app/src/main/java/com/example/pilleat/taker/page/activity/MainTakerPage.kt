package com.example.pilleat.taker.page.activity

import android.Manifest
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.example.pilleat.all.page.activity.SearchPage
import com.example.pilleat.all.page.activity.SettingPage
import com.example.pilleat.databinding.ActivityMaintakerBinding
import com.example.pilleat.taker.page.dialog.BluetoothDialog


class MainTakerPage: AppCompatActivity() {
    private lateinit var binding: ActivityMaintakerBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMaintakerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.mainTakerConnectBtn.setOnClickListener {
            // Get permission -> 위치 권한 허용
            val permission_list = arrayOf<String>(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
            )
            ActivityCompat.requestPermissions(this@MainTakerPage, permission_list, 1)

            // 다이얼로그 띄우기
            showDialog()
        }

        binding.mainTakerEnrollBtn.setOnClickListener {
            val intent = Intent(this@MainTakerPage, EnrollPillPage::class.java)
            startActivity(intent)
        }

        binding.mainTakerSearchBtn.setOnClickListener {
            val intent = Intent(this@MainTakerPage, SearchPage::class.java)
            startActivity(intent)
        }

        binding.mainTakerHomeBtn.setOnClickListener {
            val intent = Intent(this@MainTakerPage, HomeTakerPage::class.java)
            startActivity(intent)
        }

        binding.mainTakerSettingBtn.setOnClickListener {
            val intent = Intent(this@MainTakerPage, SettingPage::class.java)
            startActivity(intent)
        }
    }

    private fun showDialog() {
        BluetoothDialog(this@MainTakerPage).show()
    }
}