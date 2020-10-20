package com.alis.news.ui.everything

import android.app.SearchManager
import android.content.Context
import android.view.*
import androidx.appcompat.widget.SearchView
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
import kotlinx.android.synthetic.main.fragment_everything.*
import org.koin.android.ext.android.inject

class EverythingFragment : BaseFragment<EverythingViewModel>(R.layout.fragment_everything) {

    override val viewModel by inject<EverythingViewModel>()

    private lateinit var adapterEverything: NewsAdapter
    private var listEverything: MutableList<NewsArticles> = mutableListOf()

    override fun initializeViews() {
        adapterEverything = NewsAdapter(listEverything)
        val layoutManagerEverything = LinearLayoutManager(context)
        recycler_everything.apply {
            addItemDecoration(
                DividerItemDecoration(
                    context,
                    layoutManagerEverything.orientation
                )
            )
            layoutManager = layoutManagerEverything
            adapter = adapterEverything
        }
    }

    override fun setUpListeners() {
        swipeRefresh()
        clickAdapter()
        addOnScrollRecycler()
    }

    private fun swipeRefresh() {
        swipeRefresh_everything.setOnRefreshListener {
            adapterEverything.clear()
            fetchEverything()
        }
    }

    private fun clickAdapter() {
        adapterEverything.setOnItemClickListener(object : NewsAdapter.OnItemClickListener {
            override fun onNewsItemClick(item: NewsArticles) {
                DetailsFragment.start(
                    requireActivity(),
                    R.id.action_navigation_everything_to_detailsFragment,
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

    private fun addOnScrollRecycler() {
        recycler_everything.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (!recyclerView.canScrollVertically(1)) {
                    viewModel.isPagination.value = true
                    fetchEverything()
                }
            }
        })
    }

    override fun observe() {
        fetchEverything()

        subscribeToNews()
        subscribeToNewsdb()
    }

    private fun fetchEverything() {
        if (isOnline(requireActivity())) {
            shimmer_everything.startShimmer()
            viewModel.fetchEverythingFromAPI()
        } else {
            viewModel.getAllFromDatabase()
            showToastShort(requireContext(), R.string.toast_check_internet_connection)
            swipeRefresh_everything.stopRefresh()
        }
    }

    private fun subscribeToNews() {
        viewModel.news.observe(viewLifecycleOwner, {
            val articles = it.data?.articles
            when (it.status) {
                Status.LOADING -> {
                    if (viewModel.isPagination.value == true) {
                        progress_everything.visible()
                    }
                }
                Status.SUCCESS -> {
                    shimmer_everything.stopShimmer()
                    swipeRefresh_everything.stopRefresh()
                    progress_everything.gone()
                    if (articles != null) {
                        adapterEverything.addAll(articles)
                    }
                }
            }
        })
    }

    private fun subscribeToNewsdb() {
        viewModel.newsdb.observe(viewLifecycleOwner, {
            adapterEverything.addAll(it)
        })
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_toolbar_main, menu)
        searchLogic(menu)
        clearLogic(menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    private fun searchLogic(menu: Menu) {
        val searchView: SearchView = menu.findItem(R.id.search_menu).actionView as SearchView
        val searchManager =
            requireActivity().getSystemService(Context.SEARCH_SERVICE) as SearchManager
        searchView.setSearchableInfo(searchManager.getSearchableInfo(requireActivity().componentName))

        val viewSearch: View = searchView.findViewById(androidx.appcompat.R.id.search_plate)
        viewSearch.setBackgroundColor(resources.getColor(R.color.FortnigtlyPurple, null))

        searchView.setOnQueryTextListener(
            object : SimpleOnQueryTextListener(), SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    adapterEverything.clear()
                    viewModel.fetchEverythingQuery(query.toString())
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