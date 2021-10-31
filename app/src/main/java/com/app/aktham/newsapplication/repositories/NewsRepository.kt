package com.app.aktham.newsapplication.repositories

import com.app.aktham.newsapplication.retrofit.NewsApi
import javax.inject.Inject

class NewsRepository @Inject constructor(
    private val newsApi: NewsApi
) {

    fun getTopHeadlinesNews(
        countryName: String,
        language: String,
        pageSize: Int,
        category: String
    ) = newsApi.getTopHeadlinesNews(
        countryName = countryName,
        lang = language,
        category = category,
        pageSize = pageSize
    )

    fun searchNews(search: String, language: String) = newsApi.getNewsBySearch(
        searchWord = search,
        lang = language
    )

}