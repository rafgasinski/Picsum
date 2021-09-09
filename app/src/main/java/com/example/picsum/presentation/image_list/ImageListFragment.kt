package com.example.picsum.presentation.image_list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.asLiveData
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.picsum.R
import com.example.picsum.data.base.PicsumImage
import com.example.picsum.data.base.Resource
import com.example.picsum.databinding.FragmentImageListBinding
import com.example.picsum.utils.gone
import com.example.picsum.utils.visible
import com.google.android.material.snackbar.Snackbar
import org.koin.androidx.viewmodel.ext.android.viewModel

class ImageListFragment: Fragment(), ImageListAdapter.OnImageClickListener {

    private var _binding: FragmentImageListBinding? = null
    private val binding get() = _binding!!

    private val imageListViewModel: ImageListViewModel by viewModel()

    private lateinit var refreshMenuItem: MenuItem

    private val imageListAdapter by lazy {
        ImageListAdapter(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DataBindingUtil.inflate(inflater, R.layout.fragment_image_list, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.run {
            refreshMenuItem = toolbar.menu.getItem(0)
            refreshMenuItem.setOnMenuItemClickListener {
                imageListViewModel.getImages()
                true
            }

            imageListViewModel.imagesStateFlow.asLiveData().observe(viewLifecycleOwner) {
                when(it) {
                    is Resource.Loading -> {
                        progressBar.visible()
                        emptyImagesListInfo.gone()
                        if(imageListAdapter.itemCount == 0) {
                            imagesListRecyclerView.gone()
                        }
                        refreshMenuItem.isEnabled = false
                    }

                    is Resource.Success -> {
                        it.data?.takeIf { list -> list.isNotEmpty() }?.let { imagesList ->
                            progressBar.gone()
                            emptyImagesListInfo.gone()
                            imagesListRecyclerView.visible()
                            refreshMenuItem.isEnabled = true
                            imageListAdapter.submitList(imagesList)
                        }
                    }

                    is Resource.Error -> {
                        progressBar.gone()
                        imagesListRecyclerView.visible()
                        refreshMenuItem.isEnabled = true
                        if(imageListAdapter.itemCount != 0) {
                            it.message.getContentIfNotHandledOrReturnNull()?.let {
                                Snackbar.make(root, getString(R.string.error_viewing_cached_data), Snackbar.LENGTH_LONG).show()
                            }
                        } else {
                            emptyImagesListInfo.visible()
                        }
                    }
                }
            }

            imagesListRecyclerView.run {
                layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
                adapter = imageListAdapter
            }
        }
    }

    override fun onImageClicked(image: PicsumImage) {
        findNavController().navigate(ImageListFragmentDirections.actionImageListFragmentToImageDetailsFragment(image))
    }
}