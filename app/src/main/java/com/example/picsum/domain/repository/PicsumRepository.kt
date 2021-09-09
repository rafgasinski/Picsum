package com.example.picsum.domain.repository

import com.example.picsum.data.base.PicsumImage
import com.example.picsum.data.base.Resource
import kotlinx.coroutines.flow.Flow

interface PicsumRepository {
    suspend fun getImages(): Flow<Resource<List<PicsumImage>>>
}