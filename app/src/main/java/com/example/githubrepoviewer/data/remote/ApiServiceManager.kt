package com.example.githubrepoviewer.data.remote

import retrofit2.Retrofit
import javax.inject.Inject

class ApiServiceManager @Inject constructor(private val retrofitBuilder: Retrofit.Builder) {
    fun <T> provideService(service: Class<T>, baseUrl: String): T =
        retrofitBuilder.baseUrl(baseUrl).build().create(service)
}