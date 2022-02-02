package com.app.aktham.newsapplication.models

@kotlinx.serialization.Serializable
data class NewsDetailsModel(
    val title: String,
    val author: String,
    val content: String,
    val description: String,
    val publishDate: String,
    val imageUrl: String,
    val publishBy: String,
    val link: String
)