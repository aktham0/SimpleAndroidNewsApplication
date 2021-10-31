package com.app.aktham.newsapplication.retrofit

import com.app.aktham.newsapplication.retrofit.models.NewsMainResponse
import com.app.aktham.newsapplication.utils.Constants
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface NewsApi {

    @Headers("X-Api-Key:${Constants.API_KEY}")
    @GET("/v2/top-headlines")
    fun getTopHeadlinesNews(
        @Query("country") countryName: String,
        @Query("pageSize") pageSize: Int,
        @Query("language") lang :String,
        @Query("category") category :String): Call<NewsMainResponse>

    @Headers("X-Api-Key:${Constants.API_KEY}")
    @GET("/v2/everything")
    fun getNewsBySearch(
        @Query("q") searchWord: String,
        @Query("language") lang :String = "en",
        @Query("pageSize") pageSize :Int = 20
    ): Call<NewsMainResponse>

}