package com.orion.otpreader.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.orion.otpreader.R

class OtpDetailsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_otp_details)

        val message = intent.getSerializableExtra("sms")

    }
}