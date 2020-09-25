package com.alis.news

import android.app.Application
import com.alis.news.data.NewsRepository
import com.alis.news.data.remote.NewsAPIClient

class App : Application() {

    companion object {
        var newsRepository: NewsRepository? = null
    }

    override fun onCreate() {
        super.onCreate()

        val newsAPIClient = NewsAPIClient()
        newsRepository = NewsRepository(newsAPIClient)
    }
}
