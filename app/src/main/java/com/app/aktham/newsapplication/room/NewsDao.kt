package com.app.aktham.newsapplication.room

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import retrofit2.http.DELETE

@Dao
interface NewsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNewsArticle(newsArticle: NewsEntity) :Long

    @Query("DELETE FROM NewsTable")
    suspend fun deleteAll()

    @Query("SELECT * FROM NewsTable")
    fun getNewsData() :Flow<List<NewsEntity>>

    @Delete
    suspend fun deleteNewsArticle(newsEntity: NewsEntity)

}