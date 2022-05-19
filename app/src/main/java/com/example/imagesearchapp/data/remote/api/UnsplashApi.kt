package com.example.imagesearchapp.data.remote.api

import com.example.imagesearchapp.util.ApiKey
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface UnsplashApi {

    companion object{
        const val BASE_URL = "https://api.unsplash.com/"
    }

    @Headers("Accept-Version: v1", "Authorization: Client-ID ${ApiKey.API_KEY}")
    @GET("search/photos")
    suspend fun searchImages(
        @Query("query")
        query: String,
        @Query("page")
        page: Int,
        @Query("per_page")
        perPage: Int
    ): UnsplashResponse


}