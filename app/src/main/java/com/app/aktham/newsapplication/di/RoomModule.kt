package com.app.aktham.newsapplication.di

import android.content.Context
import androidx.room.Room
import com.app.aktham.newsapplication.room.NewsDataBase
import com.app.aktham.newsapplication.utils.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object RoomModule {

    @Singleton
    @Provides
    fun provideDataBase(@ApplicationContext context: Context) = Room.databaseBuilder(
        context,
        NewsDataBase::class.java,
        Constants.DB_NAME
    ).build()

    @Singleton
    @Provides
    fun provideDataBaseDao(dataBase: NewsDataBase) = dataBase.getNewsDao()
}