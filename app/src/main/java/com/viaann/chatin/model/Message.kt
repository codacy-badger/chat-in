package com.viaann.chatin.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

@Parcelize
data class Message (
    val imageUrl: String? = "",
    val username: String? = "",
    val message: String? = ""): Parcelable