package com.example.photogallery.api

import retrofit2.http.GET

private const val API_KEY = "32cdea8b7c3ece15bc1b7b8260ed024a"
private const val secret = "070d24eb315e7044"
interface FlickrApi {
    @GET(
        "services/rest/?method=flickr.interestingness.getList" +
                "&api_key=$API_KEY" +
                "&format=json" +
                "&nojsoncallback=1" +
                "&extras=url_s"
    )
    suspend fun fetchPhotos(): FlikrResponse
}