package com.example.picsum.presentation.image_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.picsum.data.base.PicsumImage
import com.example.picsum.data.base.Resource
import com.example.picsum.domain.usecase.GetImagesUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class ImageListViewModel(
    private val getImagesUseCase: GetImagesUseCase
): ViewModel() {

    private val _imagesStateFlow: MutableStateFlow<Resource<List<PicsumImage>>> =
        MutableStateFlow(Resource.Loading)

    val imagesStateFlow: StateFlow<Resource<List<PicsumImage>>> = _imagesStateFlow

    init {
        getImages()
    }

    fun getImages() {
        _imagesStateFlow.value = Resource.Loading
        viewModelScope.launch {
            getImagesUseCase()
                .collect {
                    _imagesStateFlow.value = it
                }
        }
    }
}