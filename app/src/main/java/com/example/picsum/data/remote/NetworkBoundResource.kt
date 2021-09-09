package com.example.picsum.data.remote

import com.example.picsum.data.base.Event
import com.example.picsum.data.base.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException

abstract class NetworkBoundResource<T> {
    abstract suspend fun remoteFetch(): T
    open fun shouldFetch() = true

    fun returnAsFlow(): Flow<Resource<T>> = flow {
        if(shouldFetch()) {
            try {
                emit(Resource.Success(remoteFetch()))
            } catch (e: IOException){
                emit(Resource.Error(Event(e.message)))
            } catch (e: HttpException){
                emit(Resource.Error(Event(e.message)))
            }
        }
    }
}