package com.alis.news.data.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private const val BASE_URL_NEWS_API = "https://newsapi.org/"

class RetrofitClient {

    internal val provideRetrofit = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(BASE_URL_NEWS_API)
        .build()

    fun provideNews(): NewsAPI = provideRetrofit.create(NewsAPI::class.java)
}