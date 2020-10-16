package com.alis.news.data.local.db

import android.app.Application
import androidx.room.Room

class DatabaseClient {

    internal fun provideDatabase(application: Application): NewsDatabase {
        return Room.databaseBuilder(
            application,
            NewsDatabase::class.java,
            "news.database"
        )
            .fallbackToDestructiveMigration()
            .allowMainThreadQueries()
            .build()
    }

    fun provideNews(database: NewsDatabase): NewsDao {
        return database.newsDao()
    }
}