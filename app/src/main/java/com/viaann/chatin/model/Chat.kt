package com.viaann.chatin.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Chat(

    @SerializedName("getUsername")
    var username: String? = "",

    @SerializedName("getMessage")
    var message: String? = "",

    @SerializedName("getSender")
    var sender: String? = "",

    @SerializedName("getReceiver")
    var receiver: String? = ""): Parcelable