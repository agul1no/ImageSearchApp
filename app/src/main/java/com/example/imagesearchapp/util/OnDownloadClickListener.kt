package com.example.imagesearchapp.util

import com.example.imagesearchapp.data.model.UnsplashImage

interface OnDownloadClickListener {

    fun onDownloadButtonClick(image: UnsplashImage)
}