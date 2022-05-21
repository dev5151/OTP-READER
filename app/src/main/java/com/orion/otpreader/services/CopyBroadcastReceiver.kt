package com.orion.otpreader.services

import android.content.*
import android.widget.Toast


class CopyBroadcastReceiver(private val otp: String) : BroadcastReceiver() {
    override fun onReceive(context: Context?, p1: Intent?) {
        val clipboard: ClipboardManager =
            context?.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val clip = ClipData.newPlainText("otp_copy", otp)
        clipboard.setPrimaryClip(clip)
        Toast.makeText(context, "Copied!", Toast.LENGTH_SHORT).show()
    }
}