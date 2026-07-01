package com.example.perangkatlembaga.Home

import retrofit2.http.GET

interface NewsApiService {
    @GET("cnn-news/")
    suspend fun getNews(): NewsResponse
}