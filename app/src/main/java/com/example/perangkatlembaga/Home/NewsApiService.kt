package com.example.perangkatlembaga.Home

import retrofit2.http.GET

interface NewsApiService {
    @GET("antara/terbaru/")
    suspend fun getNews(): NewsResponse
}