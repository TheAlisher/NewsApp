package com.alis.news.models

import java.io.Serializable

data class NewsArticles(
    var source: NewsSource? = null,
    var author: String? = null,
    var title: String? = null,
    var description: String? = null,
    var url: String? = null,
    var urlToImage: String? = null,
    var publishedAt: String? = null,
    var content: String? = null,
) : Serializable