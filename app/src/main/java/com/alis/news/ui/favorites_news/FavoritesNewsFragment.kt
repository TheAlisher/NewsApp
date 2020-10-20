package com.alis.news.ui.favorites_news

import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.alis.news.R
import com.alis.news.adapters.NewsAdapter
import com.alis.news.base.BaseFragment
import com.alis.news.extension.gone
import com.alis.news.extension.visible
import com.alis.news.models.NewsArticles
import com.alis.news.ui.details.DetailsFragment
import kotlinx.android.synthetic.main.fragment_favorites_news.*
import org.koin.android.ext.android.inject

class FavoritesNewsFragment :
    BaseFragment<FavoritesNewsViewModel>(R.layout.fragment_favorites_news) {

    override val viewModel by inject<FavoritesNewsViewModel>()

    private lateinit var adapterFavorites: NewsAdapter
    private var listFavorites: MutableList<NewsArticles> = mutableListOf()

    override fun initializeViews() {
        createRecyclerFavorites()
        setUpFavorites()
    }

    private fun createRecyclerFavorites() {
        adapterFavorites = NewsAdapter(listFavorites)
        val layoutManagerFavorites = LinearLayoutManager(context)
        recycler_favorites.apply {
            addItemDecoration(
                DividerItemDecoration(
                    context,
                    layoutManagerFavorites.orientation
                )
            )
            layoutManager = layoutManagerFavorites
            adapter = adapterFavorites
        }
    }

    private fun setUpFavorites() {
        viewModel.getAllFromDatabase()
        if (listFavorites.toString().isEmpty()) {
            text_favorites_empty.visible()
        } else {
            text_favorites_empty.gone()
        }
    }

    override fun setUpListeners() {
        clickAdapter()
    }

    private fun clickAdapter() {
        adapterFavorites.setOnItemClickListener(object : NewsAdapter.OnItemClickListener {
            override fun onNewsItemClick(item: NewsArticles) {
                DetailsFragment.start(
                    requireActivity(),
                    R.id.action_navigation_favorites_to_detailsFragment,
                    item
                )
            }

            override fun onNewsItemLikeClick(item: NewsArticles) {
                if (item.isFavorite) {
                    item.isFavorite = false
                    viewModel.deleteFavoriteNews(item)
                } else {
                    item.isFavorite = true
                    viewModel.insertFavoriteNews(item)
                }
            }
        })
    }

    override fun observe() {
        viewModel.news.observe(viewLifecycleOwner, {
            adapterFavorites.addAll(it)
        })
    }
}