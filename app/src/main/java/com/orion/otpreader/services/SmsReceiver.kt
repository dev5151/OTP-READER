package com.orion.otpreader.services

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.provider.Telephony
import android.telephony.SmsMessage
import android.util.Log
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.getSystemService
import com.google.android.gms.auth.api.phone.SmsRetriever
import com.google.android.gms.common.api.CommonStatusCodes
import com.google.android.gms.common.api.Status
import com.orion.otpreader.R
import com.orion.otpreader.data.model.SmsModel
import com.orion.otpreader.ui.MainActivity
import com.orion.otpreader.utils.NotificationUtil
import com.orion.otpreader.utils.NotificationUtil.Companion.sendNotification
import com.orion.otpreader.utils.Utils

class SmsReceiver : BroadcastReceiver() {
    // declaring variables
    lateinit var notificationManager: NotificationManager


    override fun onReceive(context: Context?, intent: Intent?) {
        Log.i("msg", "received")

        notificationManager = ContextCompat.getSystemService(
            context!!,
            NotificationManager::class.java
        ) as NotificationManager


        val smsMessages = Telephony.Sms.Intents.getMessagesFromIntent(intent)
        if (smsMessages != null) {
            Log.i("msg", smsMessages.toString())
            for (message in smsMessages) {
                Log.i("msg", message.toString())
                Toast.makeText(
                    context,
                    "Message from ${message.displayOriginatingAddress} : body ${message.messageBody}",
                    Toast.LENGTH_SHORT
                )
                    .show()
                val text = message.messageBody
                if (Utils.isOtpTypeMessage(text)) {
                   val smsModel = SmsModel(
                        null,
                        message.displayOriginatingAddress,
                        message.messageBody,
                        null,
                        null,
                        null,
                        null
                    )
                    notificationManager.sendNotification(
                        Utils.extractOtpFromMessage(text)!!,
                        context,
                        smsModel
                    )
                }
            }
        }
    }
}
