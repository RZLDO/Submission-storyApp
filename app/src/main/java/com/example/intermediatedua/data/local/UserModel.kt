package com.example.intermediatedua.data.local


import android.os.Parcelable
import kotlinx.parcelize.Parcelize


@Parcelize
data class UserModel(
    var userId : String,
    var name: String,
    var token : String,
):Parcelable
