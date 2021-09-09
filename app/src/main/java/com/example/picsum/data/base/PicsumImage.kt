package com.example.picsum.data.base

import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.parcelize.Parcelize

@Parcelize
data class PicsumImage(
    @field:Json(name = "id") val id: Int,
    @field:Json(name = "author") val author: String,
    @field:Json(name = "width") val width: Int,
    @field:Json(name = "height") val height: Int,
    @field:Json(name = "url") val url: String,
    @field:Json(name = "download_url") val downloadUrl: String
): Parcelable