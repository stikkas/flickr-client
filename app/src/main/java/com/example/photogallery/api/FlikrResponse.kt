package com.example.photogallery.api

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class FlikrResponse(val photos: PhotoResponse)