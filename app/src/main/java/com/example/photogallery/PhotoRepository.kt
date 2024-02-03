package com.example.photogallery

import com.example.photogallery.api.FlickrApi
import com.example.photogallery.model.GallaryItem
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.create

class PhotoRepository {
    private val api: FlickrApi

    init {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.flickr.com/")
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
        api = retrofit.create<FlickrApi>()
    }

    suspend fun fetchPhotos(): List<GallaryItem> {
        return api.fetchPhotos().photos.galleryItems
    }
}