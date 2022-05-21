package com.orion.otpreader.utils

import com.orion.otpreader.data.model.SmsModel
import java.util.regex.Matcher
import java.util.regex.Pattern

class Utils {

    companion object {
        fun isOtpTypeMessage(sms: String): Boolean {
            val pattern: Pattern = Pattern.compile("(|^)\\d{6}")
            val matcher: Matcher = pattern.matcher(sms)
            if (!matcher.find()) {
                return false
            }
            return true
        }

        fun extractOtpFromMessage(sms:String): String? {
            var otp: String? = null
            val pattern: Pattern = Pattern.compile("(|^)\\d{6}")
            val matcher: Matcher = pattern.matcher(sms)
            if (matcher.find()) {
                otp = matcher.group(0)!!.toString()
            }
            return otp
        }
    }
}