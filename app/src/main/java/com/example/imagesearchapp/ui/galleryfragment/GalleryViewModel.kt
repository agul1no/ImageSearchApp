package com.example.imagesearchapp.ui.galleryfragment

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.example.imagesearchapp.data.repository.UnsplashRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class GalleryViewModel @Inject constructor
    (
    private val repository: UnsplashRepository
    ) : ViewModel() {

    companion object{
        private const val DEFAULT_QUERY = ""
    }

    private val currentQuery = MutableLiveData(DEFAULT_QUERY)

    val images = currentQuery.switchMap { query->
        repository.getSearchResult(query).cachedIn(viewModelScope)
    }

    fun searchImages (query: String){
        currentQuery.value = query
    }
}