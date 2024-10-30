package com.example.techtally

import android.os.Parcel
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class SamsungGalaxyS24Review(
    val smartphoneId: Int,
    val username: String,
    val rating: Int,
    val comment: String
) : Parcelable

@Parcelize
data class Iphone16ProMaxReview(
    val smartphoneId: Int,
    val username: String,
    val rating: Int,
    val comment: String
) : Parcelable

@Parcelize
data class AppleMacbookM3ProReview(
    val smartphoneId: Int,
    val username: String,
    val rating: Int,
    val comment: String
) : Parcelable