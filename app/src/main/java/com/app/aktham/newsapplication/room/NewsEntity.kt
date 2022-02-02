package com.app.aktham.newsapplication.room

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.app.aktham.newsapplication.models.NewsModel

@Entity(tableName = "NewsTable")
data class NewsEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val title: String,
    val content: String,
    val body: String,
    val author: String,
    val publishBy: String,
    val imageUrl :String,
    val publishDate: String,
    val link: String
)


fun NewsEntity.toNewsModel() :NewsModel {
    return NewsModel(
        newsTitle = title,
        newsContent = content,
        newsDescription = body,
        newsLink = link,
        newsPublishBy = publishBy,
        newsImageUrl = imageUrl,
        newsPublishDate = publishDate,
        newsAuthor = author
    )
}