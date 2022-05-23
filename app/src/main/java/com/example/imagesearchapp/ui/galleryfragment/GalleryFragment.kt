package com.example.imagesearchapp.ui.galleryfragment

import android.content.Context
import android.os.Bundle
import android.view.*
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.example.imagesearchapp.R
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

        binding.rvGallery.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)

                if (!recyclerView.canScrollVertically(1)){
                    adapter.retry()
                }
            }
        })

        viewModel.images.observe(viewLifecycleOwner, Observer {
            adapter.submitData(viewLifecycleOwner.lifecycle, it)
        })

        setHasOptionsMenu(true)

        return binding.root
    }

    private fun initializesRecyclerView() : UnsplashImageAdapter{
        val adapter = UnsplashImageAdapter()
        binding.rvGallery.setHasFixedSize(true)
        binding.rvGallery.adapter = adapter.withLoadStateHeaderAndFooter(
            header = UnsplashImageLoadStateAdapter { adapter.retry() },
            footer = UnsplashImageLoadStateAdapter { adapter.retry() }
        )
        return adapter
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)

        inflater.inflate(R.menu.search_menu_gallery, menu)

        val searchItem = menu.findItem(R.id.action_search)
        val searchView = searchItem.actionView as SearchView

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query?.isEmpty() == true){
                    binding.tvNoQuery.visibility = View.VISIBLE
                }
                if (query?.isNotEmpty() == true){
                    binding.rvGallery.scrollToPosition(0)
                    viewModel.searchImages(query.toString())
                    binding.tvNoQuery.visibility = View.GONE
                    searchView.clearFocus()
                }
                return true
            }
            override fun onQueryTextChange(query: String?): Boolean {
                return true
            }

        })
    }

    fun View.hideKeyboard() {
        val inputMethodManager = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(windowToken, 0)
    }
}