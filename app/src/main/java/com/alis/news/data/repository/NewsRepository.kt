package com.alis.news.data.repository

import androidx.lifecycle.liveData
import com.alis.news.data.local.db.NewsDao
import com.alis.news.data.remote.NewsAPI
import com.alis.news.data.remote.Resource
import com.alis.news.models.NewsArticles
import kotlinx.coroutines.Dispatchers

class NewsRepository(
    private val newsAPI: NewsAPI,
    private val newsDao: NewsDao,
) {

    fun fetchTopHeadlines(
        country: String? = null,
        q: String? = null,
        pageSize: Int? = null,
        page: Int? = null
    ) = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            val result = newsAPI.fetchTopHeadlines(country, q, pageSize, page)
            emit(Resource.success(data = result))
            //newsDao.insertAll(result.articles)
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

    fun deleteAll() {
        newsDao.deleteAll()
    }

    fun getAll(): List<NewsArticles>? {
        return newsDao.getAll()
    }
}