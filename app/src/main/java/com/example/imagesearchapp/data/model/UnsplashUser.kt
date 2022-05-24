package com.example.imagesearchapp.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class UnsplashUser(
    val id: String,
    val username: String,
    val name: String,
    @SerializedName("first_name")
    val firstName: String,
    @SerializedName("last_name")
    val lastName: String,
    @SerializedName("instagram_username")
    val instagramUsername: String,
    @SerializedName("twitter_username")
    val twitterUsername: String,
    @SerializedName("portfolio_url")
    val portfolioUrl: String,
    @SerializedName("profile_image")
    val profileImage: ProfileImage
) : Parcelable {
    val attributionUrl get() = "http://unsplash.com/$username?utm_source=ImageSearchApp&utm_medium=referral"
}
