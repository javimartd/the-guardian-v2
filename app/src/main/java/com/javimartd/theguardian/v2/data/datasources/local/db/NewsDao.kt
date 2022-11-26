package com.javimartd.theguardian.v2.data.datasources.local.db

import androidx.room.*
import com.javimartd.theguardian.v2.data.datasources.local.model.NewsLocalData
import com.javimartd.theguardian.v2.data.datasources.local.model.NewsLocalUpdateData
import kotlinx.coroutines.flow.Flow

@Dao
interface NewsDao {

    @Query("SELECT * FROM news")
    fun getAll(): Flow<List<NewsLocalData>>

    @Query("DELETE FROM news")
    suspend fun removeAll()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(news: List<NewsLocalData>)

    @Update(entity = NewsLocalData::class)
    suspend fun update(news: NewsLocalUpdateData)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(news: NewsLocalData)

    @Query("SELECT * FROM news WHERE newsId == :newsId")
    fun findById(newsId: String): Flow<NewsLocalData>

    @Query("DELETE FROM news WHERE newsId == :newsId")
    suspend fun removeById(newsId: String)

    @Delete
    suspend fun removeItem(news: NewsLocalData)
}