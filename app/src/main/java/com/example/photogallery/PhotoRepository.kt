package com.example.photogallery

import com.example.photogallery.api.FlickrApi
import com.example.photogallery.api.PhotoInterceptor
import com.example.photogallery.model.GalleryItem
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.create

class PhotoRepository {
    private val api: FlickrApi

    init {
        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(PhotoInterceptor())
            .build()
        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.flickr.com/")
            .addConverterFactory(MoshiConverterFactory.create())
            .client(okHttpClient)
            .build()
        api = retrofit.create<FlickrApi>()
    }

    suspend fun fetchPhotos(): List<GalleryItem> {
        return api.fetchPhotos().photos.galleryItems
    }

    suspend fun searchPhotos(query: String): List<GalleryItem> {
        return api.searchPhotos(query).photos.galleryItems
    }
}