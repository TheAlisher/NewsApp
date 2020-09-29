package com.alis.news.ui.main

import android.content.Context
import android.net.ConnectivityManager
import android.os.Bundle
import android.view.*
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.alis.news.R
import com.alis.news.adapters.NewsAdapter
import com.alis.news.extension.showToastLONG
import com.alis.news.interfaces.OnItemClickListener
import com.alis.news.models.NewsArticles
import com.alis.news.ui.details.DetailsFragment.Companion.ARG_NEWS_DATA

class MainFragment : Fragment() {

    private lateinit var viewModel: MainViewModel

    private lateinit var recyclerNews: RecyclerView
    private lateinit var newsAdapter: NewsAdapter
    private var list: ArrayList<NewsArticles> = ArrayList()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initializationViewModel()
        observe()
        createRecycler(view)
        getNews()
        initializationListeners()
    }

    private fun initializationViewModel() {
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
    }

    private fun observe() {
        observeNews()
        observeSearchNews()
        observeIsEndpoint()
    }

    private fun observeNews() {
        viewModel.news.observe(viewLifecycleOwner,
            { articles ->
                list.addAll(articles!!)
                newsAdapter.notifyDataSetChanged()
            })
    }

    private fun observeSearchNews() {
        viewModel.searchNews.observe(viewLifecycleOwner,
            { articles ->
                list.clear()
                list.addAll(articles)
                newsAdapter.notifyDataSetChanged()
            })
    }

    private fun observeIsEndpoint() {
        viewModel.isEndpoint.observe(viewLifecycleOwner,
            {
                list.clear()
                requestToApi()
            }
        )
    }

    private fun createRecycler(view: View) {
        recyclerNews = view.findViewById(R.id.recycler_main)
        val layoutManager = LinearLayoutManager(context)
        val dividerItemDecoration =
            DividerItemDecoration(recyclerNews.context, layoutManager.orientation)
        recyclerNews.layoutManager = layoutManager
        recyclerNews.addItemDecoration(dividerItemDecoration)
        newsAdapter = NewsAdapter(list)
        recyclerNews.adapter = newsAdapter
        addRecyclerListener()
    }

    private fun addRecyclerListener() {
        recyclerNews.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (!recyclerView.canScrollVertically(1)) {
                    requestToApi()
                }
            }
        })
    }

    private fun getNews() {
        if (isNetworkAvailable()) {
            requestToApi()
        } else {
            requestToDatabase()
        }
    }

    private fun isNetworkAvailable(): Boolean {
        val connectivityManager =
            requireActivity().getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?
        val activeNetworkInfo = connectivityManager!!.activeNetworkInfo
        return activeNetworkInfo != null && activeNetworkInfo.isConnected
    }

    private fun requestToApi() {
        viewModel.requestToAPI()
    }

    private fun requestToDatabase() {
        viewModel.requestToDatabase()
    }

    private fun initializationListeners() {
        newsAdapter.setOnItemClickListener(object : OnItemClickListener {
            override fun onNewsItemClick(position: Int) {
                val bundle = Bundle()
                bundle.putSerializable(ARG_NEWS_DATA, list[position])
                findNavController().navigate(R.id.action_mainFragment_to_detailsFragment, bundle)
                /*DetailsFragment.start(
                    R.id.action_mainFragment_to_detailsFragment,
                    list[position]
                )*/
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.toolbar_menu, menu)
        searchLogic(menu)
        switchLogic(menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    private fun searchLogic(menu: Menu) {
        val itemSearch = menu.findItem(R.id.search_menu)
        val searchView: androidx.appcompat.widget.SearchView = itemSearch.actionView
                as androidx.appcompat.widget.SearchView
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener,
            androidx.appcompat.widget.SearchView.OnQueryTextListener {

            override fun onQueryTextChange(p0: String?): Boolean {
                return true
            }

            override fun onQueryTextSubmit(p0: String?): Boolean {
                viewModel.searchRequestToAPI(p0)
                return true
            }
        })
    }

    private fun switchLogic(menu: Menu) {
        val topHeadlines = menu.findItem(R.id.top_headlines_menu)
        topHeadlines.setOnMenuItemClickListener {
            showToastLONG(requireContext(), "В ленте будут отображаться \"Главные заголовки\"")
            viewModel.clickTopHeadlines()
            true
        }
        val everything = menu.findItem(R.id.everything_menu)
        everything.setOnMenuItemClickListener {
            showToastLONG(requireContext(), "В ленте будут отображаться \"Все\"")
            viewModel.clickEverything()
            true
        }
    }
}