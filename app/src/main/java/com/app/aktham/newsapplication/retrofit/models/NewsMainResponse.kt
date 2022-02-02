package com.app.aktham.newsapplication.retrofit.models

import com.app.aktham.newsapplication.models.NewsModel
import com.app.aktham.newsapplication.models.NewsSearchModel
import com.app.aktham.newsapplication.room.NewsEntity

data class NewsMainResponse(
    val articles: List<Article>,
    val status: String,
    val totalResults: Int
)

// mapping data for NewsMainResponse to NewListModel
fun NewsMainResponse.toNewsListModel(): List<NewsModel> {
    val newsListModel = mutableListOf<NewsModel>()
    articles.forEach {
        newsListModel.add(
            NewsModel(
                newsTitle = it.title ?: "",
                newsAuthor = it.author ?: "",
                newsImageUrl = it.urlToImage ?: "",
                newsPublishDate = it.publishedAt ?: "",
                newsPublishBy = it.source.name ?: "",
                newsContent = it.content ?: "",
                newsDescription = it.description ?: "",
                newsLink = it.url ?: ""
            )
        )
    }

    return newsListModel
}

// mapping data from MainNewResponse Model To NewsEntity Model
fun NewsMainResponse.toNewsEntity(): List<NewsEntity> {
    val newsEntityList = mutableListOf<NewsEntity>()
    articles.forEach {
        NewsEntity(
            title = it.title ?: "",
            author = it.author ?: "",
            publishBy = it.source.name ?: "",
            publishDate = it.publishedAt ?: "",
            imageUrl = it.urlToImage ?: "",
            content = it.content ?: "",
            body = it.description ?: "",
            link = it.url ?: ""
        )
    }

    return newsEntityList
}