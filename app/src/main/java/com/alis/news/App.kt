package com.alis.news

import android.app.Application
import androidx.room.Room
import com.alis.news.data.NewsRepository
import com.alis.news.data.remote.NewsAPIClient
import com.alis.news.db.NewsDatabase

class App : Application() {

    companion object {
        var newsRepository: NewsRepository? = null
        var newsDatabase: NewsDatabase? = null
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
        newsRepository = NewsRepository(newsAPIClient, newsDatabase!!.newsDao())
    }
}
