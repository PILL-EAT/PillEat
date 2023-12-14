package com.example.pilleat.protector.page.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.pilleat.R
import com.example.pilleat.WebSocketManager
import com.example.pilleat.all.page.activity.SearchPage
import com.example.pilleat.all.page.activity.SettingPage
import com.example.pilleat.databinding.ActivityMainprotectorBinding
import org.json.JSONObject

class MainProtectorPage: AppCompatActivity(){
    private lateinit var binding: ActivityMainprotectorBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainprotectorBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        val webSocket = WebSocketManager.getInstance(this).getWebSocket()
//
//        // JSON 데이터 생성
//        val jsonData = createJsonData()
//
//        // WebSocket을 통해 JSON 데이터 전송
//        webSocket.send(jsonData.toString())

        binding.mainProtectorHomeBtn.setOnClickListener {
            val intent = Intent(this@MainProtectorPage, HomeProtectorPage::class.java)
            intent.putExtra("protectorId", getData())
            startActivity(intent)
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

//    private fun createJsonData(): JSONObject {
//        val jsonData = JSONObject()
//        jsonData.put("type", "protector") // 원하는 키-값 쌍을 추가할 수 있습니다.
//        return jsonData
//    }

}