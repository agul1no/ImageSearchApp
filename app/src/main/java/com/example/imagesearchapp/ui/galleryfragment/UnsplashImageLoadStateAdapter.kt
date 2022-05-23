package com.example.imagesearchapp.ui.galleryfragment

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.imagesearchapp.databinding.UnsplashImageLoadStateFooterBinding

class UnsplashImageLoadStateAdapter (private val retry: () -> Unit) : LoadStateAdapter<UnsplashImageLoadStateAdapter.LoadStateViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): LoadStateViewHolder {
        val binding = UnsplashImageLoadStateFooterBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return LoadStateViewHolder(binding)
    }

    override fun onBindViewHolder(holder: LoadStateViewHolder, loadState: LoadState) {
        holder.bind(loadState)
    }

    inner class LoadStateViewHolder( // Inner class so we are able to access the properties of the surrounding class. ViewHolder and Adapter are tightly coupled.
        private val binding: UnsplashImageLoadStateFooterBinding
        ): RecyclerView.ViewHolder(binding.root){

        init { // better so set the onClickListener here than in the onBindViewHolder cause method will be repeated over and over again for every item that appears in the screen. The ViewHolder will be only a few times created depending on the size of the item layout
            binding.tvError.setOnClickListener {
                retry.invoke()
            }
        }

            fun bind(loadState: LoadState){
                binding.apply {
                    progressBar.isVisible = loadState is LoadState.Loading
                    tvError.isVisible = loadState !is LoadState.Loading
                }
            }
        }
}