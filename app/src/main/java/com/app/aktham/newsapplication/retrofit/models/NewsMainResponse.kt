package com.app.aktham.newsapplication.retrofit.models

data class NewsMainResponse(
    val articles: List<Article>,
    val status: String,
    val totalResults: Int
)