package com.alis.news.data.local.db

import android.content.Context
import androidx.room.Room

class DatabaseClient {

    internal fun provideDatabase(context: Context): NewsDatabase {
        return Room.databaseBuilder(
            context,
            NewsDatabase::class.java,
            "news.database"
        )
            .build()
    }

    fun provideNews(database: NewsDatabase): NewsDao {
        return database.newsDao()
    }
}