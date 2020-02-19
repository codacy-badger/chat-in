package com.viaann.chatin.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class User(

    @SerializedName("getIdAccount")
    var idAccount: String? = "",

    @SerializedName("getUsername")
    var username: String? = "",

    @SerializedName("getStatus")
    var status: String? = "" ,

    @SerializedName("getImageUrl")
    var imageUrl: String? = "",

    @SerializedName("getEmail")
    var email: String? = ""): Parcelable