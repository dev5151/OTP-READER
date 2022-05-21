package com.orion.otpreader.services

import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.content.ContextCompat


class DismissBroadcastReceiver : BroadcastReceiver() {
    override fun onReceive(p0: Context?, p1: Intent?) {
        val notificationId: Int = p1!!.getIntExtra("notificationId", 0)
        val notificationManager = ContextCompat.getSystemService(
            p0!!,
            NotificationManager::class.java
        ) as NotificationManager

        notificationManager.cancel(notificationId)
    }
}