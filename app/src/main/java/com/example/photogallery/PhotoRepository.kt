package com.example.photogallery

import com.example.photogallery.api.FlickrApi
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.create

class PhotoRepository {
    private val api: FlickrApi

    init {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://www.flickr.com")
            .addConverterFactory(ScalarsConverterFactory.create())
            .build()
        api = retrofit.create<FlickrApi>()
    }

    suspend fun fetchContents(): String {
        return api.fetchContents()
    }
}