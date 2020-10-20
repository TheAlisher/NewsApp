package com.alis.news.ui.favorites_news

import androidx.lifecycle.MutableLiveData
import com.alis.news.base.BaseViewModel
import com.alis.news.data.repository.NewsRepository
import com.alis.news.models.NewsArticles

class FavoritesNewsViewModel(private val newsRepository: NewsRepository) : BaseViewModel() {

    var news = MutableLiveData<List<NewsArticles>>()

    fun getAllFromDatabase() {
        news.value = newsRepository.getAll()
    }
}