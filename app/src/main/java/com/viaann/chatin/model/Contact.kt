package com.viaann.chatin.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Contact(
    val username: String? = "",
    val status: String? = "",
    val imageUrl: String? = "") : Parcelable