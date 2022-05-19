package com.example.imagesearchapp.data.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class UnsplashImage(
    val id: String,
    val description: String?,
    val urls: UnsplashImageUrls,
    val user: UnsplashUser
    ) : Parcelable {

}
