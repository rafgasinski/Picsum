package com.example.picsum.data.remote

import com.example.picsum.data.base.PicsumImage
import retrofit2.http.GET
import retrofit2.http.Query

interface PicsumApi {
    @GET("list?")
    suspend fun getImages(
        @Query("page") page: Int
    ): List<PicsumImage>
}