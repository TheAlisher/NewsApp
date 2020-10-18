package com.alis.news.ui.top_headlines

import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.SearchView.OnQueryTextListener
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.alis.news.R
import com.alis.news.adapters.NewsAdapter
import com.alis.news.base.BaseFragment
import com.alis.news.data.remote.Status
import com.alis.news.extension.*
import com.alis.news.models.NewsArticles
import com.alis.news.ui.details.DetailsFragment
import com.alis.news.utils.SimpleOnQueryTextListener
import kotlinx.android.synthetic.main.fragment_top_headlines.*
import org.koin.android.ext.android.inject

class TopHeadlinesFragment : BaseFragment<TopHeadlinesViewModel>(R.layout.fragment_top_headlines) {

    override val viewModel by inject<TopHeadlinesViewModel>()

    private lateinit var adapterTopHeadlines: NewsAdapter
    private var listTopHeadlines: MutableList<NewsArticles> = mutableListOf()

    override fun initializeViews() {
        adapterTopHeadlines = NewsAdapter(listTopHeadlines)
        val layoutManagerTopHeadlines = LinearLayoutManager(context)
        recycler_top_headlines.apply {
            addItemDecoration(
                DividerItemDecoration(
                    context,
                    layoutManagerTopHeadlines.orientation
                )
            )
            layoutManager = layoutManagerTopHeadlines
            adapter = adapterTopHeadlines
        }
    }

    override fun setUpListeners() {
        clickAdapter()
        addOnScrollRecycler()
    }

    private fun clickAdapter() {
        adapterTopHeadlines.setOnItemClickListener(object : NewsAdapter.OnItemClickListener {
            override fun onNewsItemClick(item: NewsArticles) {
                DetailsFragment.start(
                    requireActivity(),
                    R.id.action_navigation_top_headlines_to_detailsFragment,
                    item
                )
            }
        })
    }

    private fun addOnScrollRecycler() {
        recycler_top_headlines.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (!recyclerView.canScrollVertically(1)) {
                    fetchTopHeadlines()
                }
            }
        })
    }

    override fun observe() {
        fetchTopHeadlines()

        subscribeToNews()
        subscribeToNewsQuery()
    }

    private fun fetchTopHeadlines() {
        if (isOnline(requireActivity())) {
            viewModel.fetchTopHeadlinesFromAPI()
        } else {
            viewModel.getAllFromDatabase()
            showToastShort(requireContext(), R.string.toast_check_internet_connection)
        }
    }

    private fun subscribeToNews() {
        viewModel.news.observe(viewLifecycleOwner, {
            val articles = it.data?.articles
            when (it.status) {
                Status.LOADING -> progress_top_headlines.visible()
                Status.SUCCESS -> {
                    progress_top_headlines.gone()
                    if (articles != null) {
                        adapterTopHeadlines.addAll(articles)
                    }
                }
            }
        })
    }

    private fun subscribeToNewsQuery() {
        viewModel.newsQuery.observe(viewLifecycleOwner, {
            val articles = it.data?.articles
            when (it.status) {
                Status.LOADING -> progress_top_headlines.visible()
                Status.SUCCESS -> {
                    progress_top_headlines.gone()
                    listTopHeadlines.clear()
                    if (articles != null) {
                        adapterTopHeadlines.addAll(articles)
                    }
                }
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_toolbar_main, menu)
        searchLogic(menu)
        clearLogic(menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    private fun searchLogic(menu: Menu) {
        val itemSearch = menu.findItem(R.id.search_menu)
        val searchView: SearchView = itemSearch.actionView as SearchView
        searchView.setOnQueryTextListener(
            object : SimpleOnQueryTextListener(), OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    viewModel.fetchTopHeadlinesQuery(query.toString())
                    return true
                }
            })
    }

    private fun clearLogic(menu: Menu) {
        val itemClear = menu.findItem(R.id.clear_all_saved)
        itemClear.setOnMenuItemClickListener {
            viewModel.clearDatabase()
            showToastShort(requireContext(), R.string.toast_cache_cleared)
            true
        }
    }
}