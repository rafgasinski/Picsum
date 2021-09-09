package com.example.picsum.domain.repository

import com.example.picsum.data.base.PicsumImage
import com.example.picsum.data.base.Resource
import com.example.picsum.data.remote.NetworkBoundResource
import com.example.picsum.data.remote.PicsumApi
import com.example.picsum.utils.PreferencesManager
import kotlinx.coroutines.flow.Flow

class PicsumRepositoryImpl(
    private val picsumApi: PicsumApi,
    private val preferencesManager: PreferencesManager
): PicsumRepository {
    override suspend fun getImages(): Flow<Resource<List<PicsumImage>>> {
        return object: NetworkBoundResource<List<PicsumImage>>() {
            override suspend fun remoteFetch(): List<PicsumImage> {
                return picsumApi.getImages(preferencesManager.currentPage).sortedBy { it.id }
            }
        }.returnAsFlow()
    }
}