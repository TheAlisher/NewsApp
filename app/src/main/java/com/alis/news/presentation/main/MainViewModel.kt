package com.alis.news.presentation.main

import android.util.Log
import androidx.lifecycle.ViewModel
import com.alis.news.App
import com.alis.news.data.remote.NewsAPIClient
import com.alis.news.models.NewsResponse

class MainViewModel : ViewModel() {

    val news: List<NewsResponse>? = null

    fun requestInAPI() {
        App.newsRepository?.getAction(
            object : NewsAPIClient.NewsActionCallback {

                override fun onSuccess(result: NewsResponse) {
                    Log.d("anime", result.articles.toString())
                }

                override fun onFailure(exception: Exception) {
                    Log.d("anime", exception.toString())
                }
            }
        )
    }
}