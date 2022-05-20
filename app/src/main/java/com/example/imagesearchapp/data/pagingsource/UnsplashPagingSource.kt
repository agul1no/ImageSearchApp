package com.example.imagesearchapp.data.pagingsource

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.imagesearchapp.data.model.UnsplashImage
import com.example.imagesearchapp.data.remote.api.UnsplashApi
import retrofit2.HttpException
import java.io.IOException

private const val UNSPLASH_STARTING_PAGE_INDEX = 1

class UnsplashPagingSource (
    private val unsplashApi: UnsplashApi,
    private val query: String,
        ): PagingSource<Int,UnsplashImage>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, UnsplashImage> {
        val position = params.key ?: UNSPLASH_STARTING_PAGE_INDEX

        return try {
            val response = unsplashApi.searchImages(query, position, params.loadSize) // Type UnsplashResponse
            val images = response.results

            LoadResult.Page(
                data = images,
                prevKey = if (position == UNSPLASH_STARTING_PAGE_INDEX) null else position -1,
                nextKey = if (images.isEmpty()) null else position + 1
                    )
        }catch (exception: IOException){
            LoadResult.Error(exception)
        }catch (exception: HttpException){
            LoadResult.Error(exception)
        }

    }

    override fun getRefreshKey(state: PagingState<Int, UnsplashImage>): Int? {
        // not implemented on purpose cause don't needed
        return state.anchorPosition
    }
}