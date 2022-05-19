package com.example.imagesearchapp.ui.galleryfragment

import androidx.lifecycle.ViewModel
import com.example.imagesearchapp.data.repository.UnsplashRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class GalleryViewModel @Inject constructor (private val repository: UnsplashRepository) : ViewModel() {
}