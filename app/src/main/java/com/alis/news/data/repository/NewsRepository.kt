package com.alis.news.data.repository

import androidx.lifecycle.liveData
import com.alis.news.data.network.NewsAPI
import com.alis.news.data.network.Resource
import kotlinx.coroutines.Dispatchers

class NewsRepository(private val newsAPI: NewsAPI) {

    fun fetchTopHeadlines(
        country: String?,
        pageSize: Int?,
        page: Int?
    ) = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            emit(Resource.success(data = newsAPI.fetchTopHeadlines(country, pageSize, page)))
        } catch (E: Exception) {
            emit(Resource.error(data = null, message = E.message ?: "Error Occured!"))
        }
    }

    fun fetchEverything(
        country: String?,
        pageSize: Int?,
        page: Int?,
    ) = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            emit(Resource.success(data = newsAPI.fetchEverything(country, pageSize, page)))
        } catch (E: Exception) {
            emit(Resource.error(data = null, message = E.message ?: "Error Occured!"))
        }
    }

    fun fetchQuery(q: String) = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            emit(Resource.success(data = newsAPI.fetchQuery(q)))
        } catch (E: Exception) {
            emit(Resource.error(data = null, message = E.message ?: "Error Occured!"))
        }
    }
}