package com.app.aktham.newsapplication.models

data class NewsDetailsModel(
    val title: String,
    val image: String,
    val newsBody: String,
    val pageLink: String,
    val publishBy: String,
    val author: String,
    val publishDate: String,
    val description: String
)
