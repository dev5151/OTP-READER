package com.orion.otpreader.services

import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.core.content.ContextCompat


class MarkAsDoneBroadcastReceiver : BroadcastReceiver() {
    override fun onReceive(p0: Context?, p1: Intent?) {
        val messageId: Int = p1!!.getIntExtra("messageId", 0)
        val notificationId: Int = p1.getIntExtra("notificationId", 0)

        val values = ContentValues()
        values.put("read", true)
        p0!!.contentResolver.update(
            Uri.parse("content://sms/inbox"), values,
            "_id=$messageId", null
        )
        val notificationManager = ContextCompat.getSystemService(
            p0,
            NotificationManager::class.java
        ) as NotificationManager

        notificationManager.cancel(notificationId)

    }
}