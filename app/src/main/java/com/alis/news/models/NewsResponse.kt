package com.alis.news.models

data class NewsResponse(
    var status: String? = null,
    var totalResults: Int? = null,
    var articles: List<NewsArticles>
)