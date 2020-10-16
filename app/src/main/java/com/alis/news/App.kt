package com.alis.news

import android.app.Application
import androidx.room.Room
import com.alis.news.data.local.db.NewsDatabase
import com.alis.news.di.newsModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@App)
            modules(newsModule)
        }
    }
}
