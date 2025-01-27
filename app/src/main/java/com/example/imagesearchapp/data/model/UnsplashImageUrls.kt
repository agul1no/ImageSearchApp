package com.example.imagesearchapp.data.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class UnsplashImageUrls(
    val raw : String,
    val full: String,
    val regular: String,
    val small: String,
    val thumb: String
    ) : Parcelable {

}
