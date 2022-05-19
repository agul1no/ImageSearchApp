package com.example.imagesearchapp.data.repository

import com.example.imagesearchapp.data.remote.api.UnsplashApi
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UnsplashRepository @Inject constructor (private val unsplashApi: UnsplashApi){
}