package com.example.photogallery.api

import retrofit2.http.GET
import retrofit2.http.Query

private const val secret = "070d24eb315e7044"

interface FlickrApi {
    @GET("services/rest?method=flickr.interestingness.getList")
    suspend fun fetchPhotos(): FlikrResponse

    @GET("services/rest?method=flickr.photos.search")
    suspend fun searchPhotos(@Query("text") query: String): FlikrResponse
}