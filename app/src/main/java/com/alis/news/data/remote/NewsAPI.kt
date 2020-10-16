package com.alis.news.data.remote

import com.alis.news.models.NewsResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsAPI {

    @GET("v2/top-headlines?apiKey=bd9cafc7bcbd4767a804a034c271569b")
    suspend fun fetchTopHeadlines(
        @Query("country") country: String?,
        @Query("pageSize") pageSize: Int?,
        @Query("page") page: Int?
    ): NewsResponse

    @GET("v2/everything?apiKey=bd9cafc7bcbd4767a804a034c271569b")
    suspend fun fetchEverything(
        @Query("country") country: String?,
        @Query("pageSize") pageSize: Int?,
        @Query("page") page: Int?
    ) : NewsResponse

    @GET("v2/top-headlines?apiKey=bd9cafc7bcbd4767a804a034c271569b")
    suspend fun fetchTopHeadlinesQuery(
        @Query("q") q: String?
    ): NewsResponse

    @GET("v2/everything?apiKey=bd9cafc7bcbd4767a804a034c271569b")
    suspend fun fetchEverythingQuery(
        @Query("q") q: String?
    ): NewsResponse
}