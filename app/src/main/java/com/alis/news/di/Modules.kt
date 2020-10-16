package com.alis.news.di

import com.alis.news.data.AppPreferences
import com.alis.news.data.remote.RetrofitClient
import com.alis.news.data.repository.NewsRepository
import com.alis.news.ui.everything.EverythingViewModel
import com.alis.news.ui.top_headlines.TopHeadlinesViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

var newsModule = module {

    single { AppPreferences(get()) }
    single { RetrofitClient().provideNews() }

    factory { NewsRepository(get()) }

    viewModel { TopHeadlinesViewModel(get()) }
    viewModel { EverythingViewModel(get()) }
}