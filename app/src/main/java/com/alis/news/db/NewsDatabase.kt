package com.alis.news.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.alis.news.db.converter.SourceConverter
import com.alis.news.models.NewsArticles

@Database(entities = [NewsArticles::class], version = 1)
abstract class NewsDatabase : RoomDatabase() {
    abstract fun newsDao(): NewsDao
}