package com.example.imagesearchapp.api

import com.example.imagesearchapp.model.UnsplashImage

data class UnsplashResponse (
    val results: List<UnsplashImage>
        )