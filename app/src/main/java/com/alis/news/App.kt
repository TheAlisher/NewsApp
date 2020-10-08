package com.alis.news

import android.app.Application
import androidx.room.Room
import com.alis.news.data.AppPreferences
import com.alis.news.data.NewsRepository
import com.alis.news.data.remote.NewsAPIClient
import com.alis.news.db.NewsDatabase

class App : Application() {

    companion object {
        lateinit var newsRepository: NewsRepository
        lateinit var newsDatabase: NewsDatabase
        lateinit var preferences: AppPreferences
    }

    override fun onCreate() {
        super.onCreate()

        val newsAPIClient = NewsAPIClient()
        newsDatabase = Room.databaseBuilder(
            this,
            NewsDatabase::class.java,
            "news.database"
        )
            .fallbackToDestructiveMigration()
            .allowMainThreadQueries()
            .build()
        newsRepository = NewsRepository(newsAPIClient, newsDatabase.newsDao())
        preferences = AppPreferences(this)
    }
}
