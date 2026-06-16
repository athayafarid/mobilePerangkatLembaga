package com.example.perangkatlembaga.data.api

import com.example.perangkatlembaga.data.model.CatFactModel
import retrofit2.http.GET

interface CatFactApiService {
    @GET("fact")
    suspend fun getCatFact(): CatFactModel
}