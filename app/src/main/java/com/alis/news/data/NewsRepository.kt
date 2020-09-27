package com.alis.news.data

import com.alis.news.data.remote.NewsAPIClient
import com.alis.news.models.NewsArticles
import com.alis.news.models.NewsResponse

class NewsRepository(newsAPIClient: NewsAPIClient) {

    private var newsAPIClient: NewsAPIClient? = newsAPIClient

    fun getAction(
        country: String?,
        pageSize: Int?,
        page: Int?,
        callback: NewsAPIClient.NewsActionCallback
    ) {
        newsAPIClient?.getAction(
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
}