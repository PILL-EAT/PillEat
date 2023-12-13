package com.example.pilleat

import android.util.Log
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okhttp3.WebSocket
import okhttp3.WebSocketListener

class WebSocketManager private constructor() {
    private val client = OkHttpClient()
    private var webSocket: WebSocket? = null

    companion object {
        private var instance: WebSocketManager? = null

        fun getInstance(): WebSocketManager {
            if (instance == null) {
                instance = WebSocketManager()
            }
            return instance!!
        }
    }

    fun getWebSocket(): WebSocket {
        if (webSocket == null) {
            // WebSocket 연결 초기화 로직
            val request = Request.Builder().url("ws://ceprj.gachon.ac.kr:60013/ws").build()
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
    }
}