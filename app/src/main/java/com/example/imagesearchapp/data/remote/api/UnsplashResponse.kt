package com.example.imagesearchapp.data.remote.api

import com.example.imagesearchapp.data.model.UnsplashImage

data class UnsplashResponse (
    val results: List<UnsplashImage>
        )