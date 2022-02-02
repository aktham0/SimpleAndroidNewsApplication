package com.app.aktham.newsapplication.repositories

import com.app.aktham.newsapplication.retrofit.NewsApi
import com.app.aktham.newsapplication.room.NewsDao
import com.app.aktham.newsapplication.room.NewsEntity
import javax.inject.Inject

class NewsRepository @Inject constructor(
    private val newsApi: NewsApi,
    private val newsDao: NewsDao
) {

    suspend fun getTopHeadlinesNews(
        language: String,
        pageSize: Int,
        category: String
    ) = newsApi.getTopHeadlinesNews(
        newsContentLanguage = language,
        pageSize = pageSize,
        category =  category
    )

    suspend fun searchNews(
        search: String,
        language: String
    ) = newsApi.getNewsBySearch(
        searchWord = search,
        newsContentLanguage = language
    )


    suspend fun insertNewsArticle(newsEntity: NewsEntity) =
        newsDao.insertNewsArticle(newsEntity)

    suspend fun getArchiveNewsArticles() = newsDao.getNewsData()

    suspend fun deleteAllNewsArticles() = newsDao.deleteAll()

    suspend fun deleteNewsArticle(newsEntity: NewsEntity) = newsDao.deleteNewsArticle(newsEntity)

}