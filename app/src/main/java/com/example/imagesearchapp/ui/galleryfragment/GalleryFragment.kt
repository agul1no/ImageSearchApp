package com.example.imagesearchapp.ui.galleryfragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.imagesearchapp.databinding.FragmentGalleryBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class GalleryFragment : Fragment() {

    private var _binding: FragmentGalleryBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: GalleryViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentGalleryBinding.inflate(inflater, container, false)

        viewModel = ViewModelProvider(this)[GalleryViewModel::class.java]

        val adapter = initializesRecyclerView()

        viewModel.images.observe(viewLifecycleOwner, Observer {
            adapter.submitData(viewLifecycleOwner.lifecycle, it)
        })

        return binding.root
    }

    private fun initializesRecyclerView() : UnsplashImageAdapter{
        val adapter = UnsplashImageAdapter()
        binding.rvGallery.setHasFixedSize(true)
        binding.rvGallery.adapter = adapter
        return adapter
    }

}