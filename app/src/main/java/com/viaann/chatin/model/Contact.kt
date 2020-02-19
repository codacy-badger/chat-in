package com.viaann.chatin.model

import android.os.Parcel
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Contact(
    val idAccount: String? = "",
    val username: String? = "",
    val status: String? = "",
    val imageUrl: String? = "") : Parcelable