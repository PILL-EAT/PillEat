package com.example.pilleat.taker.page.activity

import android.Manifest
import android.content.Intent
import android.os.Bundle
import android.os.Parcelable
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.example.pilleat.all.page.activity.SearchPage
import com.example.pilleat.all.page.activity.SettingPage
import com.example.pilleat.all.response.Result
import com.example.pilleat.databinding.ActivityMaintakerBinding
import com.example.pilleat.taker.page.dialog.BluetoothDialog
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okhttp3.WebSocket
import okhttp3.WebSocketListener


class MainTakerPage : AppCompatActivity() {
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

    private fun showDialog() {
        BluetoothDialog(this@MainTakerPage).show()
    }
}