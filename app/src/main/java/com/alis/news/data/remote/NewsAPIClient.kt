package com.alis.news.data.remote

import android.util.Log
import com.alis.news.core.CoreCallback
import com.alis.news.models.NewsResponse
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

class NewsAPIClient {

    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl("https://newsapi.org/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val client: NewsAPI = retrofit.create(NewsAPI::class.java)

    fun getTopHeadlines(
        country: String?,
        pageSize: Int?,
        page: Int?,
        callback: NewsActionCallback
    ) {
        val call: Call<NewsResponse> = client.getTopHeadlines(
            country,
            pageSize,
            page
        )

        Log.d("getTopHeadlines", call.request().url().toString())

        call.enqueue(object : CoreCallback<NewsResponse>() {
            override fun onSuccess(result: NewsResponse) {
                callback.onSuccess(result)
            }

            override fun onFailure(exception: Exception) {
                callback.onFailure(exception)
            }
        })
    }

    fun getEverything(
        country: String?,
        pageSize: Int?,
        page: Int?,
        callback: NewsActionCallback
    ) {
        val call: Call<NewsResponse> = client.getTopHeadlines(
            country,
            pageSize,
            page
        )

        Log.d("getEverything", call.request().url().toString())

        call.enqueue(object : CoreCallback<NewsResponse>() {
            override fun onSuccess(result: NewsResponse) {
                callback.onSuccess(result)
            }

            override fun onFailure(exception: Exception) {
                callback.onFailure(exception)
            }
        })
    }

    fun getActionWithSearch(
        q: String?,
        callback: NewsActionCallback
    ) {
        val call: Call<NewsResponse> = client.getActionWithSearch(
            q
        )

        Log.d("getSearchAction", call.request().url().toString())

        call.enqueue(object : CoreCallback<NewsResponse>() {
            override fun onSuccess(result: NewsResponse) {
                callback.onSuccess(result)
            }

            override fun onFailure(exception: Exception) {
                callback.onFailure(exception)
            }
        })
    }

    interface NewsActionCallback : BaseCallback<NewsResponse> {

    }

    interface BaseCallback<T> {
        fun onSuccess(result: T)
        fun onFailure(exception: Exception)
    }

    interface NewsAPI {
        @GET("v2/top-headlines?apiKey=bd9cafc7bcbd4767a804a034c271569b")
        fun getTopHeadlines(
            @Query("country") country: String?,
            @Query("pageSize") pageSize: Int?,
            @Query("page") page: Int?
        ): Call<NewsResponse>

        @GET("v2/everything?apiKey=bd9cafc7bcbd4767a804a034c271569b")
        fun getEverything(
            @Query ("country") country: String?,
            @Query("pageSize") pageSize: Int?,
            @Query("page") page: Int?
        )

        @GET("v2/everything?apiKey=bd9cafc7bcbd4767a804a034c271569b")
        fun getActionWithSearch(
            @Query("q") q: String?
        ): Call<NewsResponse>
    }
}
