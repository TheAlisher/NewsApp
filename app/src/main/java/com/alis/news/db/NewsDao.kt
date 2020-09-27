package com.alis.news.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.alis.news.models.NewsArticles

@Dao
interface NewsDao {

    @Insert
    fun insert(newsArticles: List<NewsArticles>)

    @Delete
    fun delete(newsArticles: NewsArticles)

    @Query("SELECT * FROM newsArticles")
    fun getAll(): List<NewsArticles>?

    @Query("DELETE FROM newsArticles")
    fun deleteAll()
}