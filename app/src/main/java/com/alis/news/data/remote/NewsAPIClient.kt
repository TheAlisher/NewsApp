package com.alis.news.data.remote

import android.util.Log
import com.alis.news.core.CoreCallback
import com.alis.news.models.NewsArticles
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

    fun getAction(
        country: String?,
        pageSize: Int?,
        page: Int?,
        callback: NewsActionCallback
    ) {
        val call: Call<NewsResponse> = client.getAction(
            country,
            pageSize,
            page
        )

        Log.d("call", call.request().url().toString())

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
        fun getAction(
            @Query("country") country: String?,
            @Query("pageSize") pageSize: Int?,
            @Query("page") page: Int?
        ): Call<NewsResponse>
    }
}
