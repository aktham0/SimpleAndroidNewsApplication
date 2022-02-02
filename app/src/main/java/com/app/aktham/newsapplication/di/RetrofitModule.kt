package com.app.aktham.newsapplication.di

import com.app.aktham.newsapplication.retrofit.NewsApi
import com.app.aktham.newsapplication.utils.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object RetrofitModule {

    @Singleton
    @Provides
    fun providesRetrofit(): Retrofit = Retrofit.Builder()
        .baseUrl(Constants.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    @Singleton
    @Provides
    fun providesNewsApi(retrofit: Retrofit): NewsApi =
        retrofit.create(NewsApi::class.java)

}