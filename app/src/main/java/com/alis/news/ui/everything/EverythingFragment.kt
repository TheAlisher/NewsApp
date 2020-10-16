package com.alis.news.ui.everything

import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.alis.news.R
import com.alis.news.adapters.NewsAdapter
import com.alis.news.data.remote.Status
import com.alis.news.extension.*
import com.alis.news.models.NewsArticles
import com.alis.news.ui.details.DetailsFragment
import com.alis.news.utils.SimpleOnQueryTextListener
import kotlinx.android.synthetic.main.fragment_everything.*
import kotlinx.android.synthetic.main.fragment_top_headlines.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class EverythingFragment : Fragment() {

    private val viewModel by viewModel<EverythingViewModel>()

    private lateinit var adapterEverything: NewsAdapter
    private var listEverything: MutableList<NewsArticles> = mutableListOf()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)
        return inflater.inflate(R.layout.fragment_everything, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initializeViews()
        setUpListeners()
        observe()
    }

    private fun initializeViews() {
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

    private fun setUpListeners() {
        clickAdapter()
        addOnScrollRecycler()
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
        })
    }

    private fun addOnScrollRecycler() {
        recycler_everything.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (!recyclerView.canScrollVertically(1)) {
                    viewModel.fetchEverythingFromAPI()
                }
            }
        })
    }

    private fun observe() {
        fetchEverything()

        subscribeToNews()
        subscribeToNewsQuery()
    }

    private fun fetchEverything() {
        if (isNetworkAvailable(requireActivity())) {
            viewModel.fetchEverythingFromAPI()
        } else {
            viewModel.fetchEverythingFromDatabase()
        }
    }

    private fun subscribeToNews() {
        viewModel.news.observe(viewLifecycleOwner, {
            val articles = it.data?.articles
            when (it.status) {
                Status.LOADING -> progress_everything.visible()
                Status.SUCCESS -> {
                    progress_everything.gone()
                    if (articles != null) {
                        adapterEverything.addAll(articles)
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
                    listEverything.clear()
                    if (articles != null) {
                        adapterEverything.addAll(articles)
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
            object : SimpleOnQueryTextListener(), SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
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