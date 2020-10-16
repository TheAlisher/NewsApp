package com.alis.news.ui.everything

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.alis.news.data.remote.Resource
import com.alis.news.data.repository.NewsRepository
import com.alis.news.models.NewsResponse

class EverythingViewModel(private val newsRepository: NewsRepository) : ViewModel() {

    var news = MutableLiveData<Resource<NewsResponse>>()
    var newsQuery = MutableLiveData<Resource<NewsResponse>>()

    private var page: Int = 0

    fun fetchEverythingFromAPI() {
        page += 1
        news = newsRepository.fetchEverything(
            domains = "engadget.com",
            pageSize = 10,
            page = page
        ) as MutableLiveData<Resource<NewsResponse>>
    }

    fun fetchEverythingFromDatabase() {
        //TODO: fetch news from db
    }

    fun fetchEverythingQuery(q: String) {
        newsQuery = newsRepository.fetchEverything(
            q = q
        ) as MutableLiveData<Resource<NewsResponse>>
    }

    fun clearDatabase() {
        //TODO: clear
    }
}