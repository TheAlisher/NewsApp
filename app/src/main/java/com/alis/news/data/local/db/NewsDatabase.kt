package com.alis.news.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.alis.news.models.NewsArticles

@Database(entities = [NewsArticles::class], version = 1)
abstract class NewsDatabase : RoomDatabase() {
    abstract fun newsDao(): NewsDao
}