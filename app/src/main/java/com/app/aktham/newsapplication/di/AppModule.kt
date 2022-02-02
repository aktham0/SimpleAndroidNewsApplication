package com.app.aktham.newsapplication.di

import android.content.Context
import android.content.SharedPreferences
import com.app.aktham.newsapplication.utils.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object AppModule {

    @Singleton
    @Provides
    fun providesSharedPreference(@ApplicationContext context: Context): SharedPreferences =
        context.getSharedPreferences(
            Constants.SHARED_PREFERENCE_FILE_NAME,
            Context.MODE_PRIVATE
        )

}

