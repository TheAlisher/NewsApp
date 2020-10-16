package com.alis.news.ui.top_headlines

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.SearchView.OnQueryTextListener
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.alis.news.R
import com.alis.news.adapters.NewsAdapter
import com.alis.news.data.remote.Status
import com.alis.news.extension.invisible
import com.alis.news.extension.isNetworkAvailable
import com.alis.news.extension.showToastShort
import com.alis.news.extension.visible
import com.alis.news.models.NewsArticles
import com.alis.news.ui.details.DetailsFragment
import com.alis.news.utils.SimpleOnQueryTextListener
import kotlinx.android.synthetic.main.fragment_top_headlines.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class TopHeadlinesFragment : Fragment() {

    private val viewModel by viewModel<TopHeadlinesViewModel>()

    private lateinit var adapterTopHeadlines: NewsAdapter
    private var listTopHeadlines: MutableList<NewsArticles> = mutableListOf()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)
        return inflater.inflate(R.layout.fragment_top_headlines, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initializeViews()
        setUpListeners()
        observe()
    }

    private fun initializeViews() {
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

    private fun setUpListeners() {
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
                    viewModel.fetchTopHeadlinesFromAPI()
                }
            }
        })
    }

    private fun observe() {
        fetchTopHeadlines()

        subscribeToNews()
        subscribeToNewsQuery()
    }

    private fun fetchTopHeadlines() {
        if (isNetworkAvailable(requireActivity())) {
            viewModel.fetchTopHeadlinesFromAPI()
            //viewModel.fetchTopHeadlinesQuery("bitcoin")
        } else {
            viewModel.fetchTopHeadlinesFromDatabase()
        }
    }

    private fun subscribeToNews() {
        viewModel.news.observe(viewLifecycleOwner, {
            val articles = it.data?.articles
            when (it.status) {
                Status.LOADING -> progress_top_headlines.visible()
                Status.SUCCESS -> {
                    progress_top_headlines.invisible()
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
                    progress_top_headlines.invisible()
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