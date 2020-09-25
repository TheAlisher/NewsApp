package com.alis.news.presentation.main

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.alis.news.App
import com.alis.news.data.remote.NewsAPIClient
import com.alis.news.models.NewsArticles
import com.alis.news.models.NewsResponse

class MainViewModel : ViewModel() {

    var news: MutableLiveData<List<NewsArticles>> = MutableLiveData()

    private var mNews: List<NewsArticles>? = null

    fun requestInAPI() {
        App.newsRepository?.getAction(
            object : NewsAPIClient.NewsActionCallback {

                override fun onSuccess(result: NewsResponse) {
                    Log.d("anime", result.articles.toString())
                    mNews = result.articles
                    news.value = result.articles
                }

                override fun onFailure(exception: Exception) {
                    Log.d("anime", exception.toString())
                }
            }
        )
    }
}