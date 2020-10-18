package com.alis.news.ui.top_headlines

import androidx.lifecycle.MutableLiveData
import com.alis.news.base.BaseViewModel
import com.alis.news.data.remote.Resource
import com.alis.news.data.repository.NewsRepository
import com.alis.news.models.NewsArticles
import com.alis.news.models.NewsResponse

class TopHeadlinesViewModel(private val newsRepository: NewsRepository) : BaseViewModel() {

    var news = MutableLiveData<Resource<NewsResponse>>()
    var isPagination = MutableLiveData<Boolean>()
    var newsdb =  MutableLiveData<List<NewsArticles>>()

    private var page: Int = 0

    fun fetchTopHeadlinesFromAPI() {
        page += 1
        news = newsRepository.fetchTopHeadlines(
            country = "us",
            pageSize = 10,
            page = page
        ) as MutableLiveData<Resource<NewsResponse>>
    }

    fun fetchTopHeadlinesQuery(q: String) {
        news = newsRepository.fetchTopHeadlines(
            q = q
        ) as MutableLiveData<Resource<NewsResponse>>
    }

    fun getAllFromDatabase() {
        newsdb.value = newsRepository.getAll()
    }

    fun clearDatabase() {
        newsRepository.deleteAll()
    }
}