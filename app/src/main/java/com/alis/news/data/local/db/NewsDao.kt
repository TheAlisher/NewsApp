package com.alis.news.data.local.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.alis.news.models.NewsArticles

@Dao
interface NewsDao {

    @Insert
    fun insert(newsArticles: NewsArticles)

    @Insert
    fun insertAll(newsArticles: List<NewsArticles>)

    @Delete
    fun delete(newsArticles: NewsArticles)

    @Query("DELETE FROM newsArticles")
    fun deleteAll()

    @Query("SELECT * FROM newsArticles")
    fun getAll(): List<NewsArticles>?
}