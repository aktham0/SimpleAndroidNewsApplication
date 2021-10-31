package com.app.aktham.newsapplication.utils

import com.app.aktham.newsapplication.models.NewsDetailsModel
import com.app.aktham.newsapplication.models.NewsListModel
import com.app.aktham.newsapplication.models.NewsSearchModel

interface DataMapping<T> {
    fun responseToModel(response: T) :List<NewsListModel>
    fun responseToDetailNewsModel(response: T) :List<NewsDetailsModel>
    fun responseToSearchModel(response: T) :List<NewsSearchModel>
}