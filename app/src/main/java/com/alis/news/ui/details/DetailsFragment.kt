package com.alis.news.ui.details

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.navigation.Navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import com.alis.news.R
import com.alis.news.extension.loadImage
import com.alis.news.models.NewsArticles
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.fragment_details.*

class DetailsFragment : Fragment() {

    companion object {
        private var item: NewsArticles? = null
        fun start(activity: Activity, action: Int, item: NewsArticles) {
            this.item = item
            findNavController(activity, R.id.nav_host_fragment).navigate(action)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setNewsData()
    }

    private fun setNewsData() {
        image_details_news.loadImage(item?.urlToImage)
        text_details_title.text = item?.title
        text_details_description.text = item?.description
    }
}