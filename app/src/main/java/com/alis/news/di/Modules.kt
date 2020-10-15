package com.alis.news.di

import com.alis.news.data.NewsRepository
import com.alis.news.data.remote.NewsAPIClient
import org.koin.dsl.module

var newsModule = module {

    single { NewsAPIClient() }
    factory { NewsRepository(get()) }
}