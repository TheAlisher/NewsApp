package com.alis.news.ui.top_headlines

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.alis.news.data.network.Resource
import com.alis.news.data.repository.NewsRepository
import com.alis.news.models.NewsResponse

class TopHeadlinesViewModel(private val newsRepository: NewsRepository) : ViewModel() {

    var news = MutableLiveData<Resource<NewsResponse>>()

    private var page: Int = 1

    init {
        fetchTopHeadlines()
    }

    private fun fetchTopHeadlines() {
        page += 1
        news = newsRepository.fetchTopHeadlines(
            "us",
            10,
            page
        ) as MutableLiveData<Resource<NewsResponse>>
    }

    /* var news: MutableLiveData<List<NewsArticles>> = MutableLiveData()
     var searchNews: MutableLiveData<List<NewsArticles>> = MutableLiveData()
     var isEndpoint: MutableLiveData<Boolean> = MutableLiveData()

     private var mNews: List<NewsArticles>? = null
     private var page: Int = 0

     fun requestToAPI() {
         incrementPage()
         if (App.preference?.endpoint()!!) {
             App.newsRepository?.getTopHeadlines(
                 "us",
                 10,
                 page,
                 object : NewsAPIClient.NewsActionCallback {

                     override fun onSuccess(result: NewsResponse) {
                         Log.d("topHeadlinesRequest", result.articles.toString())
                         mNews = result.articles
                         news.value = result.articles
                     }

                     override fun onFailure(exception: Exception) {
                         Log.d("topHeadlinesRequest", exception.toString())
                     }
                 }
             )
         } else {
             App.newsRepository?.getEverything(
                 "us",
                 10,
                 page,
                 object : NewsAPIClient.NewsActionCallback {

                     override fun onSuccess(result: NewsResponse) {
                         Log.d("everythingRequest", result.articles.toString())
                         mNews = result.articles
                         news.value = result.articles
                     }

                     override fun onFailure(exception: Exception) {
                         Log.d("everythingRequest", exception.toString())
                     }
                 }
             )
         }
     }

     private fun incrementPage() {
         page += 1
     }

     fun requestToDatabase() {
         news.value = App.newsDatabase!!.newsDao().getAll()
     }

     fun searchRequestToAPI(q: String?) {
         App.newsRepository?.getActionWithSearch(
             q,
             object : NewsAPIClient.NewsActionCallback {

                 override fun onSuccess(result: NewsResponse) {
                     Log.d("searchRequestToApi", result.articles.toString())
                     searchNews.value = result.articles
                     insertInDatabase(result)
                 }

                 override fun onFailure(exception: Exception) {
                     Log.d("searchRequestToApi", exception.toString())
                 }
             }
         )
     }

     private fun insertInDatabase(result: NewsResponse) {
         App.newsDatabase!!.newsDao().insert(result.articles)
     }

     fun clickTopHeadlines() {
         page = 0
         isEndpoint.value = true
         App.preference?.setEndpoint(true)
     }

     fun clickEverything() {
         page = 0
         isEndpoint.value = false
         App.preference?.setEndpoint(false)
     }

     fun clickClear() {
         App.newsDatabase!!.newsDao().deleteAll()
         news.value = null
     }*/
}