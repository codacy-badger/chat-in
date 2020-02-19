package com.viaann.chatin.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize


@Parcelize
data class Message (
    val imageUrl: String? = "",
    val username: String? = "",
    val message: String? = "",
    val idAccount: String? = ""): Parcelable