package com.orion.otpreader.data.model

import java.io.Serializable

data class SmsModel(
    val id: String?,
    val address: String?,
    val msg: String?,
    val readState: String?,
    val time: String?,
    val folderName: String?,
    val tag: String?
) : Serializable

