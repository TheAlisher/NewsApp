package com.alis.news.ui.top_headlines

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.alis.news.data.remote.Resource
import com.alis.news.data.repository.NewsRepository
import com.alis.news.models.NewsResponse

class TopHeadlinesViewModel(private val newsRepository: NewsRepository) : ViewModel() {

    var news = MutableLiveData<Resource<NewsResponse>>()
    var newsQuery = MutableLiveData<Resource<NewsResponse>>()

    private var page: Int = 0

    fun fetchTopHeadlinesFromAPI() {
        page += 1
        news = newsRepository.fetchTopHeadlines(
            "us",
            10,
            page
        ) as MutableLiveData<Resource<NewsResponse>>
    }

    fun fetchTopHeadlinesFromDatabase() {
        //TODO: fetch news from db
    }

    fun fetchTopHeadlinesQuery(q: String) {
        newsQuery = newsRepository.fetchTopHeadlinesQuery(
            q
        ) as MutableLiveData<Resource<NewsResponse>>
    }

    fun clearDatabase() {
        //TODO: clear db
    }
}