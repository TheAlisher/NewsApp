package com.alis.news.di

import com.alis.news.data.AppPreferences
import com.alis.news.data.local.db.DatabaseClient
import com.alis.news.data.remote.RetrofitClient
import com.alis.news.data.repository.NewsRepository
import com.alis.news.ui.everything.EverythingViewModel
import com.alis.news.ui.favorites_news.FavoritesNewsViewModel
import com.alis.news.ui.top_headlines.TopHeadlinesViewModel
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

var newsModule = module {

    single { AppPreferences(get()) }
    single { RetrofitClient().provideNews() }
    single { DatabaseClient().provideDatabase(androidApplication()) }
    single { DatabaseClient().provideNews(get()) }
    single { DatabaseClient().provideFavoriteNews(get()) }

    factory { NewsRepository(get(), get(), get()) }

    viewModel { TopHeadlinesViewModel(get()) }
    viewModel { EverythingViewModel(get()) }
    viewModel { FavoritesNewsViewModel(get()) }
}