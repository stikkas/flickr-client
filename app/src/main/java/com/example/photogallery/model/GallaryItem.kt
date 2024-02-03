package com.example.photogallery.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class GallaryItem(
    val title: String, val id: String,
    @Json(name = "url_s")
    val url: String
)