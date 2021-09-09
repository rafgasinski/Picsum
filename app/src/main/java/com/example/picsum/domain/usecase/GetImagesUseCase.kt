package com.example.picsum.domain.usecase

import com.example.picsum.data.base.FlowUseCase
import com.example.picsum.data.base.PicsumImage
import com.example.picsum.data.base.Resource
import com.example.picsum.domain.repository.PicsumRepository
import kotlinx.coroutines.flow.Flow

class GetImagesUseCase(
    private val repository: PicsumRepository
): FlowUseCase<Nothing?, List<PicsumImage>>() {
    override suspend fun execute(parameters: Nothing?): Flow<Resource<List<PicsumImage>>> {
        return repository.getImages()
    }
}