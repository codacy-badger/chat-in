package com.viaann.chatin.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Contact(
    val userId: String?,
    val username: String?,
    val status: String?) : Parcelable