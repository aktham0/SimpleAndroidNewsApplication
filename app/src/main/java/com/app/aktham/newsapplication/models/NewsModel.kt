package com.app.aktham.newsapplication.models

import android.os.Parcelable
import com.app.aktham.newsapplication.room.NewsEntity
import kotlinx.android.parcel.Parcelize

@Parcelize
data class NewsModel(
    val newsTitle: String,
    val newsPublishBy: String,
    val newsImageUrl: String,
    val newsAuthor: String,
    val newsPublishDate: String,
    val newsContent: String,
    val newsLink: String,
    val newsDescription: String
) :Parcelable


fun NewsModel.toNewsEntity() :NewsEntity {
    return NewsEntity(
        title = newsTitle,
        publishDate = newsPublishDate,
        link = newsLink,
        imageUrl = newsImageUrl,
        content = newsContent,
        body = newsDescription,
        author = newsAuthor,
        publishBy = newsPublishBy
    )
}