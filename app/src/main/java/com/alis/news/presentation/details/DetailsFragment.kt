package com.alis.news.presentation.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation.findNavController
import androidx.navigation.fragment.NavHostFragment.findNavController
import com.alis.news.R
import com.alis.news.models.NewsArticles
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.fragment_details.*

class DetailsFragment : Fragment() {

    companion object {
        const val ARG_NEWS_DATA: String = "news_data"
        /*fun start(action: Int, newsArticles: NewsArticles) {
            val bundle = Bundle()
            bundle.putSerializable(ARG_NEWS_DATA, newsArticles)
            findNavController().navigate(action, bundle)
        }*/
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getNews()
    }

    private fun getNews() {
        if (arguments != null) {
            val newsArticles: NewsArticles =
                arguments?.getSerializable(ARG_NEWS_DATA) as NewsArticles
            Glide
                .with(requireContext())
                .load(newsArticles.urlToImage)
                .into(image_details_news)
            text_details_title.text = newsArticles.title
            text_details_description.text = newsArticles.description
        }
    }
}