package com.app.aktham.newsapplication.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import retrofit2.http.DELETE

@Dao
interface NewsDao {

    @Insert
    suspend fun insertNewsArticle(newsArticle: NewsEntity)

    @Query("DELETE FROM NewsTable")
    suspend fun deleteAll()

    @Query("SELECT * FROM NewsTable")
    fun getNewsData() :Flow<List<NewsEntity>>

    @Delete
    suspend fun deleteNewsArticle(newsEntity: NewsEntity)

}