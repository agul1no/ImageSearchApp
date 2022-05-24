package com.example.imagesearchapp.ui.galleryfragment

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.example.imagesearchapp.R
import com.example.imagesearchapp.data.model.UnsplashImage
import com.example.imagesearchapp.databinding.ItemUnsplashImageBinding
import com.example.imagesearchapp.util.OnRecyclerViewItemClickListener

class UnsplashImageAdapter (private val listener: OnRecyclerViewItemClickListener) : PagingDataAdapter<UnsplashImage, UnsplashImageAdapter.ImageViewHolder>(IMAGE_COMPARATOR) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        val binding = ItemUnsplashImageBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return ImageViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        val currentItem = getItem(position)

        if (currentItem != null){
            holder.bind(currentItem)
        }
    }

    inner class ImageViewHolder(
        private val binding: ItemUnsplashImageBinding
        ) : RecyclerView.ViewHolder(binding.root){

        init {
            binding.root.setOnClickListener {
                val position = bindingAdapterPosition
                if (position != RecyclerView.NO_POSITION){
                    val item = getItem(position)
                    if (item != null){
                        listener.onItemClick(item)
                    }
                }



            }
        }

            fun bind(image: UnsplashImage){
                binding.apply {
                    Glide.with(itemView)
                        .load(image.urls.regular)
                        .centerCrop()
                        .transition(DrawableTransitionOptions.withCrossFade())
                        .error(R.drawable.ic_error)
                        .into(ivImage)
                    tvUserName.text = image.user.username
                }
            }
        }

    companion object{

        private val IMAGE_COMPARATOR = object : DiffUtil.ItemCallback<UnsplashImage>(){
            override fun areItemsTheSame(oldItem: UnsplashImage, newItem: UnsplashImage) =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: UnsplashImage, newItem: UnsplashImage
            ) = oldItem == newItem
        }
    }
}