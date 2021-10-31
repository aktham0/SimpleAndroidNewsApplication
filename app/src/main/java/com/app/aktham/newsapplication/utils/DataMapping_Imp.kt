package com.app.aktham.newsapplication.utils

import com.app.aktham.newsapplication.models.NewsDetailsModel
import com.app.aktham.newsapplication.models.NewsListModel
import com.app.aktham.newsapplication.models.NewsSearchModel
import com.app.aktham.newsapplication.retrofit.models.NewsMainResponse

class DataMapping_Imp : DataMapping<NewsMainResponse> {
    override fun responseToModel(response: NewsMainResponse): List<NewsListModel> {
        val articles = response.articles
        val listHolder :MutableList<NewsListModel> = mutableListOf()
        articles.forEach {
            listHolder.add(
                NewsListModel(
                    newsTitle = it.title ?: "",
                    newsAuthor = it.author ?: "",
                    newsImageUrl = it.urlToImage ?: "",
                    newsPublishBy = it.source.name ?: "",
                    newsPublishDate = it.publishedAt ?: ""
                )
            )
        }
        return listHolder
    }

    override fun responseToDetailNewsModel(response: NewsMainResponse): List<NewsDetailsModel> {
        val articles = response.articles
        val listHolder :MutableList<NewsDetailsModel> = mutableListOf()
        articles.forEach {
            listHolder.add(
                NewsDetailsModel(
                    title = it.title ?: "",
                    image = it.urlToImage ?: "",
                    publishBy = it.source.name ?: "",
                    description = it.description ?: "",
                    pageLink = it.url ?: "",
                    publishDate = it.publishedAt ?: "",
                    author = it.author ?: "",
                    newsBody = it.content ?: ""
                )
            )
        }

        return listHolder
    }

    override fun responseToSearchModel(response: NewsMainResponse): List<NewsSearchModel> {
        val articles = response.articles
        val listHolder :MutableList<NewsSearchModel> = mutableListOf()
        articles.forEach {
            listHolder.add(
                NewsSearchModel(
                    searchTitle = it.title ?: "",
                    searchImage = it.urlToImage ?: "",
                    searchPublishBy = it.source.name ?: ""
                )
            )
        }

        return listHolder
    }
}