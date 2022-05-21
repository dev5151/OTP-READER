package com.orion.otpreader.utils

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.view.View
import android.widget.RemoteViews
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationCompat.PRIORITY_MAX
import com.orion.otpreader.R
import com.orion.otpreader.data.model.SmsModel
import com.orion.otpreader.services.CopyBroadcastReceiver
import com.orion.otpreader.services.DismissBroadcastReceiver
import com.orion.otpreader.ui.MainActivity
import java.util.concurrent.atomic.AtomicInteger


class NotificationUtil {


    companion object {
        private val NOTIFICATION_ID = AtomicInteger(0).incrementAndGet()
        private val REQUEST_CODE = 0


        fun NotificationManager.sendNotification(
            messageBody: String,
            applicationContext: Context,
            smsModel: SmsModel
        ) {

            val contentIntent = Intent(applicationContext, MainActivity::class.java)
            contentIntent.putExtra("sms", smsModel)

            //Copy Action Intent
            val intentFilter = IntentFilter("ACTION_COPY")
            applicationContext.applicationContext.registerReceiver(
                CopyBroadcastReceiver(messageBody),
                intentFilter
            )
            val copy = Intent("ACTION_COPY")
            val otpCopy =
                PendingIntent.getBroadcast(
                    applicationContext,
                    0,
                    copy,
                    PendingIntent.FLAG_CANCEL_CURRENT
                )

            //Dismiss Intent
            val intentFilterDismiss = IntentFilter("ACTION_DISMISS")
            applicationContext.applicationContext.registerReceiver(
                DismissBroadcastReceiver(),
                intentFilterDismiss
            )
            val dismiss = Intent("ACTION_DISMISS")
            dismiss.putExtra("notificationId", NOTIFICATION_ID)
            val dismissIntent = PendingIntent.getBroadcast(
                applicationContext,
                0,
                dismiss,
                PendingIntent.FLAG_CANCEL_CURRENT
            )

            //Mark As Done Intent
            val intentFilterDone = IntentFilter("ACTION_READ")
            applicationContext.applicationContext.registerReceiver(
                DismissBroadcastReceiver(),
                intentFilterDone
            )
            val read = Intent("ACTION_READ")
            read.putExtra("messageId", smsModel.id)
            read.putExtra("notificationId", NOTIFICATION_ID)
            val readIntent = PendingIntent.getBroadcast(
                applicationContext,
                0,
                read,
                PendingIntent.FLAG_CANCEL_CURRENT
            )

            val remoteViews =
                RemoteViews(applicationContext.packageName, R.layout.layout_custom_notification)
            remoteViews.setTextViewText(R.id.otp_text, messageBody)
            remoteViews.setImageViewResource(R.id.img_copy, R.drawable.ic_copy)
            remoteViews.setViewVisibility(R.id.img_copy, View.VISIBLE)
            remoteViews.setImageViewResource(R.id.user, R.drawable.lock)
            remoteViews.setTextViewText(R.id.number, smsModel.address)
            remoteViews.setOnClickPendingIntent(R.id.img_copy, dismissIntent)
            remoteViews.setOnClickPendingIntent(R.id.img_copy, otpCopy)

            val remoteViewsExpanded = RemoteViews(
                applicationContext.packageName,
                R.layout.layout_custom_notification_expanded
            )
            remoteViewsExpanded.setTextViewText(R.id.otp_text, messageBody)
            remoteViewsExpanded.setImageViewResource(R.id.img_copy, R.drawable.ic_copy)
            remoteViewsExpanded.setViewVisibility(R.id.img_copy, View.VISIBLE)
            remoteViewsExpanded.setImageViewResource(R.id.user, R.drawable.lock)
            remoteViewsExpanded.setTextViewText(R.id.done, "Mark as done")
            remoteViewsExpanded.setTextViewText(R.id.dismiss, "Dismiss")
            remoteViewsExpanded.setTextViewText(R.id.number, smsModel.address)
            remoteViewsExpanded.setOnClickPendingIntent(R.id.dismiss, dismissIntent)
            remoteViewsExpanded.setOnClickPendingIntent(R.id.img_copy, otpCopy)
            remoteViewsExpanded.setOnClickPendingIntent(R.id.done, readIntent)


            val contentPendingIntent = PendingIntent.getActivity(
                applicationContext,
                REQUEST_CODE,
                contentIntent,
                PendingIntent.FLAG_UPDATE_CURRENT
            )

            // Build the notification
            val builder = NotificationCompat.Builder(
                applicationContext,
                applicationContext.getString(R.string.notification_channel_id)
            )
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("Otp Notification")
                .setStyle(NotificationCompat.DecoratedCustomViewStyle())
                .setCustomContentView(remoteViews)
                .setCustomBigContentView(remoteViewsExpanded)
                .setContentIntent(contentPendingIntent)
                .setAutoCancel(true)
                .setPriority(PRIORITY_MAX)
            notify(NOTIFICATION_ID, builder.build())
        }

    }

}