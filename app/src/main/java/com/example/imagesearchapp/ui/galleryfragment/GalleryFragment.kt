package com.example.imagesearchapp.ui.galleryfragment

import android.content.Context
import android.content.res.Configuration
import android.graphics.Color
import android.os.Bundle
import android.view.*
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.widget.SearchView
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.paging.LoadState
import androidx.recyclerview.widget.RecyclerView
import com.example.imagesearchapp.R
import com.example.imagesearchapp.databinding.FragmentGalleryBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class GalleryFragment : Fragment() {

    private var _binding: FragmentGalleryBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: GalleryViewModel

    private var searchCounter = 0 // used for showing the indication message right after starting the app

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentGalleryBinding.inflate(inflater, container, false)

//        val searchIcon = ResourcesCompat.getDrawable(context?.resources!!, R.drawable.ic_search,null)
//
//        when (context?.resources?.configuration?.uiMode?.and(Configuration.UI_MODE_NIGHT_MASK)) {
//            Configuration.UI_MODE_NIGHT_YES -> {searchIcon?.setTint(Color.WHITE)}
//            Configuration.UI_MODE_NIGHT_NO -> {searchIcon?.setTint(R.color.teal_200)}
//            Configuration.UI_MODE_NIGHT_UNDEFINED -> { searchIcon?.setTint(R.color.teal_200)}
//        }

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

        binding.tvError.setOnClickListener {
            adapter.retry()
        }

        viewModel.images.observe(viewLifecycleOwner, Observer {
            adapter.submitData(viewLifecycleOwner.lifecycle, it)
        })

        adapter.addLoadStateListener { loadState ->
            binding.apply {
                progressBar.isVisible = loadState.source.refresh is LoadState.Loading
                rvGallery.isVisible = loadState.source.refresh is LoadState.NotLoading
                tvError.isVisible = loadState.source.refresh is LoadState.Error

                // in case of empty view
                if (loadState.source.refresh is LoadState.NotLoading && loadState.append.endOfPaginationReached && adapter.itemCount < 1 && searchCounter == 0){
                    rvGallery.isVisible = false
                    tvNoQuery.isVisible = true
                }else {
                    tvNoQuery.isVisible = false
                }
                if (loadState.source.refresh is LoadState.NotLoading && loadState.append.endOfPaginationReached && adapter.itemCount < 1 && searchCounter > 0){
                    rvGallery.isVisible = false
                    tvNoResults.isVisible = true
                }else{
                    tvNoResults.isVisible = false
                }
            }
        }

        setHasOptionsMenu(true)

        return binding.root
    }

    private fun initializesRecyclerView() : UnsplashImageAdapter{
        val adapter = UnsplashImageAdapter()
        binding.rvGallery.setHasFixedSize(true)
        binding.rvGallery.itemAnimator = null
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
                checkQueryIsEmpty(query, searchView)
                return true
            }
            override fun onQueryTextChange(query: String?): Boolean {
                return true
            }
        })
    }

    private fun checkQueryIsEmpty(query: String?, searchView: SearchView){
        if (query?.isEmpty() == true){
            binding.rvGallery.isVisible = false
            binding.tvNoQuery.isVisible = true
        }
        if (query?.isNotEmpty() == true){
            binding.rvGallery.scrollToPosition(0)
            viewModel.searchImages(query.toString())
            binding.tvNoQuery.isVisible = false
            searchView.clearFocus()
        }
        searchCounter++
    }

    fun View.hideKeyboard() {
        val inputMethodManager = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(windowToken, 0)
    }

    override fun onResume() {
        super.onResume()
        searchCounter = 0
    }
}