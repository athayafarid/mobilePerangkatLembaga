package com.example.perangkatlembaga.Home

import com.google.gson.annotations.SerializedName

data class NewsResponse(
    val success: Boolean?,
    val message: String?,
    val data: List<NewsItem>?
)

data class NewsItem(
    val title: String?,
    val link: String?,
    @SerializedName("contentSnippet") val description: String?,
    val image: NewsImage?
)

data class NewsImage(
    val small: String?,
    val medium: String?,
    val large: String?
)