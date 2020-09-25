package com.alis.news.data

import com.alis.news.data.remote.NewsAPIClient
import com.alis.news.models.NewsResponse

class NewsRepository(newsAPIClient: NewsAPIClient) {

    private var newsAPIClient: NewsAPIClient? = newsAPIClient

    fun getAction(
        callback: NewsAPIClient.NewsActionCallback
    ) {
        newsAPIClient?.getAction(
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