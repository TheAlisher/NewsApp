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
        swipeRefresh()
        clickAdapter()
        addOnScrollRecycler()
    }

    private fun swipeRefresh() {
        swipeRefresh_top_headlines.setOnRefreshListener {
            adapterTopHeadlines.clear()
            fetchTopHeadlines()
        }
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
                    viewModel.isPagination.value = true
                    fetchTopHeadlines()
                }
            }
        })
    }

    override fun observe() {
        fetchTopHeadlines()

        subscribeToNews()
    }

    private fun fetchTopHeadlines() {
        if (isOnline(requireActivity())) {
            shimmer_top_headlines.startShimmer()
            viewModel.fetchTopHeadlinesFromAPI()
        } else {
            viewModel.getAllFromDatabase()
            showToastShort(requireContext(), R.string.toast_check_internet_connection)
            swipeRefresh_top_headlines.stopRefresh()
        }
    }

    private fun subscribeToNews() {
        viewModel.news.observe(viewLifecycleOwner, {
            val articles = it.data?.articles
            when (it.status) {
                Status.LOADING -> {
                    if (viewModel.isPagination.value == true) {
                        progress_top_headlines.visible()
                    }
                }
                Status.SUCCESS -> {
                    shimmer_top_headlines.stopShimmer()
                    swipeRefresh_top_headlines.stopRefresh()
                    progress_top_headlines.gone()
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
                    adapterTopHeadlines.clear()
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