package com.example.pilleat

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okhttp3.WebSocket
import okhttp3.WebSocketListener
import org.json.JSONException
import org.json.JSONObject


const val CHANNEL_ID = "PILLEAT"
const val notificationId = 1

class WebSocketManager private constructor(private val context: Context) {
    private val client = OkHttpClient()
    private var webSocket: WebSocket? = null

    companion object {
        private var instance: WebSocketManager? = null

        fun getInstance(context: Context): WebSocketManager {
            if (instance == null) {
                instance = WebSocketManager(context)
            }
            return instance!!
        }
    }

    fun getWebSocket(): WebSocket {
        if (webSocket == null) {
            // WebSocket 연결 초기화 로직
            val request = Request.Builder().url("ws://ceprj.gachon.ac.kr:60037/ws").build()
            val listener = MyWebSocketListener()
            webSocket = client.newWebSocket(request, listener)
            client.dispatcher.executorService.shutdown()
        }
        return webSocket!!
    }

    private inner class MyWebSocketListener : WebSocketListener() {
        override fun onOpen(webSocket: WebSocket, response: Response) {
            super.onOpen(webSocket, response)
            Log.d("SOCKET-OPEN", response.message)
            // 연결이 열렸을 때의 동작
        }

        override fun onMessage(webSocket: WebSocket, text: String) {
            super.onMessage(webSocket, text)
            Log.d("SOCKET-GET", text)
            println(text)
            // 메시지 수신 시의 동작

            try {
                val json = JSONObject(text)
                val type = json.getString("type")
                val message = json.getString("message")

                if(type == "finish") {
                    showNotification(type, message)
                }

            } catch (e: JSONException) {
                e.printStackTrace()
            }
        }

        override fun onClosed(webSocket: WebSocket, code: Int, reason: String) {
            super.onClosed(webSocket, code, reason)
            Log.d("SOCKET-CLOSE", reason)
            // 연결이 닫혔을 때의 동작
        }

        override fun onFailure(webSocket: WebSocket, t: Throwable, response: Response?) {
            super.onFailure(webSocket, t, response)
            Log.d("SOCKET-FAILURE", t.toString())
            // 연결 실패 시의 동작
        }

        private fun showNotification(type: String, message: String) {
            val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                val channelId = "MyChannelId"
                val channel = NotificationChannel(
                    channelId,
                    "My Channel",
                    NotificationManager.IMPORTANCE_HIGH
                )
                notificationManager.createNotificationChannel(channel)
            }

            val notificationBuilder = NotificationCompat.Builder(context, "MyChannelId")
                .setContentTitle("PILL EAT")
                .setContentText(message)
                .setSmallIcon(R.drawable.logo_img)
                .setAutoCancel(true)

            notificationManager.notify(1, notificationBuilder.build())
            Log.d("알림창 구현", message)
        }
    }
}