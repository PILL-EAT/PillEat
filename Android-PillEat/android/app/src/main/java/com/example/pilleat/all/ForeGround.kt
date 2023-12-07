package com.example.pilleat.all

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.Binder
import android.os.Build
import android.os.Bundle
import android.os.IBinder
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.example.pilleat.R
import com.example.pilleat.taker.page.fragment.SearchFragment
import com.example.pilleat.taker.page.fragment.TakingYnFragment

class ForeGround: Service() {
    private val CHANNEL_ID = "PILLEAT23"
    private val NOTI_ID = 2023
    private var notificationManager: NotificationManager? = null

    fun createNotificationChannel() {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val serviceChannel = NotificationChannel(CHANNEL_ID, "FOREGROUND", NotificationManager.IMPORTANCE_DEFAULT)
            val manager = getSystemService(NotificationManager::class.java)
            manager.createNotificationChannel(serviceChannel)
        }
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        createNotificationChannel()
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onBind(p0: Intent?): IBinder? {
        return Binder()
    }
}