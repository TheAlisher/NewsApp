package com.alis.news.ui.main

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.alis.news.App
import com.alis.news.data.remote.NewsAPIClient
import com.alis.news.models.NewsArticles
import com.alis.news.models.NewsResponse

class MainViewModel : ViewModel() {

    var news: MutableLiveData<List<NewsArticles>> = MutableLiveData()
    var searchNews: MutableLiveData<List<NewsArticles>> = MutableLiveData()
    var isEndpoint: MutableLiveData<Boolean> = MutableLiveData()

    private var mNews: List<NewsArticles>? = null
    private var page: Int = 0

    fun requestToAPI() {
        incrementPage()
        if (App.preferences?.endpoint()!!) {
            App.newsRepository?.getTopHeadlines(
                "us",
                10,
                page,
                object : NewsAPIClient.NewsActionCallback {

                    override fun onSuccess(result: NewsResponse) {
                        Log.d("topHeadlinesRequest", result.articles.toString())
                        mNews = result.articles
                        news.value = result.articles
                    }

                    override fun onFailure(exception: Exception) {
                        Log.d("topHeadlinesRequest", exception.toString())
                    }
                }
            )
        } else {
            App.newsRepository?.getEverything(
                "us",
                10,
                page,
                object : NewsAPIClient.NewsActionCallback {

                    override fun onSuccess(result: NewsResponse) {
                        Log.d("everythingRequest", result.articles.toString())
                        mNews = result.articles
                        news.value = result.articles
                    }

                    override fun onFailure(exception: Exception) {
                        Log.d("everythingRequest", exception.toString())
                    }
                }
            )
        }
    }

    private fun incrementPage() {
        page += 1
    }

    fun requestToDatabase() {
        news.value = App.newsDatabase!!.newsDao().getAll()
    }

    fun searchRequestToAPI(q: String?) {
        App.newsRepository?.getActionWithSearch(
            q,
            object : NewsAPIClient.NewsActionCallback {

                override fun onSuccess(result: NewsResponse) {
                    Log.d("searchRequestToApi", result.articles.toString())
                    searchNews.value = result.articles
                    insertInDatabase(result)
                }

                override fun onFailure(exception: Exception) {
                    Log.d("searchRequestToApi", exception.toString())
                }
            }
        )
    }

    private fun insertInDatabase(result: NewsResponse) {
        App.newsDatabase!!.newsDao().insert(result.articles)
    }

    fun clickTopHeadlines() {
        isEndpoint.value = true
        App.preferences?.setEndpoint(true)
    }

    fun clickEverything() {
        isEndpoint.value = false
        App.preferences?.setEndpoint(false)
    }

    fun clickClear() {
        App.newsDatabase!!.newsDao().deleteAll()
        news.value = null
    }
}