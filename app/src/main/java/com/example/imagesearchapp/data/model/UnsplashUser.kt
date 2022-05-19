package com.example.imagesearchapp.data.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class UnsplashUser(
    val id: String,
    val username: String,
    val name: String,
    val first_name: String,
    val last_name: String,
    val instagram_username: String,
    val twitter_username: String,
    val portfolio_url: String,
    val profile_image: ProfileImage
) : Parcelable {
    val attributionUrl get() = "http://unsplash.com/$username?utm_source=ImageSearchApp&utm_medium=referral"
}
