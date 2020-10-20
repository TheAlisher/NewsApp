package com.alis.news.ui.details

import android.app.Activity
import androidx.navigation.Navigation.findNavController
import com.alis.news.R
import com.alis.news.base.BaseFragment
import com.alis.news.extension.loadImage
import com.alis.news.models.NewsArticles
import kotlinx.android.synthetic.main.fragment_details.*
import org.koin.android.ext.android.inject

class DetailsFragment : BaseFragment<DetailsViewModel>(R.layout.fragment_details) {

    companion object {
        private var item: NewsArticles? = null
        fun start(activity: Activity, action: Int, item: NewsArticles) {
            this.item = item
            findNavController(activity, R.id.nav_host_fragment).navigate(action)
        }
    }

    override val viewModel by inject<DetailsViewModel>()

    override fun initializeViews() {
        setNewsData()
    }

    private fun setNewsData() {
        image_details_news.loadImage(item?.urlToImage)
        text_details_title.text = item?.title
        text_details_description.text = item?.description
    }

    override fun setUpListeners() {

    }

    override fun observe() {

    }
}