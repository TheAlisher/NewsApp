package com.alis.news.ui.everything

import androidx.lifecycle.ViewModel
import com.alis.news.data.network.NewsAPI

class EverythingViewModel(private val newsAPI: NewsAPI) : ViewModel() {

    init {
        fetchEverything()
    }

    fun fetchEverything() {

    }
}