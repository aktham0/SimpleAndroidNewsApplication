package com.app.aktham.newsapplication.utils

sealed class NewsRecourses {
    object Loading : NewsRecourses()
    object Init : NewsRecourses()
    class Success<T>(val dataList: T) : NewsRecourses()
    class Errors(val error_message :String) : NewsRecourses()
}