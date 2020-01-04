package com.viaann.chatin.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

@Parcelize
data class Chat (val userId: String?,
            var username: String,
            val time: String,
            var chat: String): Parcelable