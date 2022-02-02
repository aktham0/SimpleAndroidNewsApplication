package com.app.aktham.newsapplication.retrofit

import com.app.aktham.newsapplication.retrofit.models.NewsMainResponse
import com.app.aktham.newsapplication.utils.Constants
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface NewsApi {

    // get news content
    @Headers("X-Api-Key:${Constants.API_KEY}")
    @GET("/v2/top-headlines")
    suspend fun getTopHeadlinesNews(
        @Query("pageSize") pageSize: Int,
        @Query("language") newsContentLanguage :String = "en",
        @Query("category") category :String
    ): Response<NewsMainResponse>

    // search news
    @Headers("X-Api-Key:${Constants.API_KEY}")
    @GET("/v2/everything")
    suspend fun getNewsBySearch(
        @Query("q") searchWord: String,
        @Query("language") newsContentLanguage :String = "en",
        @Query("pageSize") pageSize :Int = 20
    ): Response<NewsMainResponse>

}