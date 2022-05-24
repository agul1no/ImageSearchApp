package com.example.imagesearchapp.util

import com.example.imagesearchapp.data.model.UnsplashImage

interface OnRecyclerViewItemClickListener {
    fun onItemClick(image: UnsplashImage)
}