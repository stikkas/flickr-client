package com.example.photogallery.api

import okhttp3.Interceptor
import okhttp3.Response

private const val API_KEY = "32cdea8b7c3ece15bc1b7b8260ed024a"
class PhotoInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val newUrl = originalRequest.url.newBuilder()
            .addQueryParameter("api_key", API_KEY)
            .addQueryParameter("format", "json")
            .addQueryParameter("nojsoncallback", "1")
            .addQueryParameter("extras", "url_s")
            .addQueryParameter("safe_search", "1")
            .build()
        val request = originalRequest.newBuilder().url(newUrl).build()
        return chain.proceed(request)
    }
}