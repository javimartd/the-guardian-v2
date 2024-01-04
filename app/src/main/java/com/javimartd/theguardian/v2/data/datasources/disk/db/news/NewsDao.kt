package com.javimartd.theguardian.v2.data.datasources.disk.db.news

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface NewsDao {

    @Query("SELECT * FROM news")
    fun getAll(): Flow<List<NewsEntity>>

    @Query("DELETE FROM news")
    suspend fun removeAll()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(news: List<NewsEntity>)

    @Update(entity = NewsEntity::class)
    suspend fun update(news: NewsUpdateEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(news: NewsEntity)

    @Query("SELECT * FROM news WHERE newsId == :newsId")
    fun findById(newsId: String): Flow<NewsEntity>

    @Query("DELETE FROM news WHERE newsId == :newsId")
    suspend fun removeById(newsId: String)

    @Delete
    suspend fun removeItem(news: NewsEntity)
}