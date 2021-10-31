package com.app.aktham.newsapplication.utils

import com.app.aktham.newsapplication.models.NewsListModel

sealed class NewsRecourses {
    object Loading : NewsRecourses()
    data class Success<T>(val dataList: List<T>) : NewsRecourses()
    data class Errors(val message :String) : NewsRecourses()
    object Empty : NewsRecourses()
}