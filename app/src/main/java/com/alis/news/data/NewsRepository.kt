package com.alis.news.data

import com.alis.news.data.remote.NewsAPIClient
import com.alis.news.db.NewsDao
import com.alis.news.models.NewsArticles
import com.alis.news.models.NewsResponse

class NewsRepository(newsAPIClient: NewsAPIClient, newsDao: NewsDao) : NewsDao {

    private var newsAPIClient: NewsAPIClient? = newsAPIClient
    private var newsDao: NewsDao? = newsDao

    fun getTopHeadlines(
        country: String?,
        pageSize: Int?,
        page: Int?,
        callback: NewsAPIClient.NewsActionCallback
    ) {
        newsAPIClient?.getTopHeadlines(
            country,
            pageSize,
            page,
            object : NewsAPIClient.NewsActionCallback {
                override fun onSuccess(result: NewsResponse) {
                    callback.onSuccess(result)
                }

                override fun onFailure(exception: Exception) {
                    callback.onFailure(exception)
                }
            }
        )
    }

    fun getEverything(
        country: String?,
        pageSize: Int?,
        page: Int?,
        callback: NewsAPIClient.NewsActionCallback
    ) {
        newsAPIClient?.getEverything(
            country,
            pageSize,
            page,
            object : NewsAPIClient.NewsActionCallback {
                override fun onSuccess(result: NewsResponse) {
                    callback.onSuccess(result)
                }

                override fun onFailure(exception: Exception) {
                    callback.onFailure(exception)
                }
            }
        )
    }

    fun getActionWithSearch(
        q: String?,
        callback: NewsAPIClient.NewsActionCallback
    ) {
        newsAPIClient?.getActionWithSearch(
            q,
            object : NewsAPIClient.NewsActionCallback {
                override fun onSuccess(result: NewsResponse) {
                    callback.onSuccess(result)
                }

                override fun onFailure(exception: Exception) {
                    callback.onFailure(exception)
                }
            }
        )
    }

    override fun insert(newsArticles: List<NewsArticles>) {

    }

    override fun delete(newsArticles: NewsArticles) {
    }

    override fun getAll(): List<NewsArticles>? {
        return null
    }

    override fun deleteAll() {

    }
}