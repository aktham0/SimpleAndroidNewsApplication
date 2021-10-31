package com.app.aktham.newsapplication.di

import android.content.Context
import android.content.SharedPreferences
import com.app.aktham.newsapplication.utils.Constants
import com.app.aktham.newsapplication.utils.DataMapping_Imp
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object MainModule {

    @Singleton
    @Provides
    fun providesSharedPreference(@ApplicationContext context: Context): SharedPreferences =
        context.getSharedPreferences(
            Constants.SHARED_PREFERENCE_FILE_NAME,
            Context.MODE_PRIVATE
        )

    @Singleton
    @Provides
    fun providersDataMapping() : DataMapping_Imp = DataMapping_Imp()
}

