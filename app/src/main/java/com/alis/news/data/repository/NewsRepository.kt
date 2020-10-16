package com.alis.news.data.repository

import androidx.lifecycle.liveData
import com.alis.news.data.remote.NewsAPI
import com.alis.news.data.remote.Resource
import kotlinx.coroutines.Dispatchers

class NewsRepository(private val newsAPI: NewsAPI) {

    fun fetchTopHeadlines(
        country: String? = null,
        q: String? = null,
        pageSize: Int? = null,
        page: Int? = null
    ) = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            emit(Resource.success(data = newsAPI.fetchTopHeadlines(country, q, pageSize, page)))
        } catch (E: Exception) {
            emit(Resource.error(data = null, message = E.message ?: "Error Occured!"))
        }
    }

    fun fetchEverything(
        q: String? = null,
        domains: String? = null,
        pageSize: Int? = null,
        page: Int? = null,
    ) = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            emit(Resource.success(data = newsAPI.fetchEverything(q, domains, pageSize, page)))
        } catch (E: Exception) {
            emit(Resource.error(data = null, message = E.message ?: "Error Occured!"))
        }
    }
}