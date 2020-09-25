package com.alis.news.presentation.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.alis.news.R
import com.alis.news.adapters.NewsAdapter
import com.alis.news.interfaces.OnItemClickListener
import com.alis.news.models.NewsArticles
import com.alis.news.presentation.details.DetailsFragment
import com.alis.news.presentation.details.DetailsFragment.Companion.ARG_NEWS_DATA

class MainFragment : Fragment() {

    private lateinit var viewModel: MainViewModel

    private lateinit var recyclerNews: RecyclerView
    private lateinit var newsAdapter: NewsAdapter
    private var list: ArrayList<NewsArticles> = ArrayList()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initializationViewModel()
        observeNews()
        createRecycler(view)
        requestInAPI()
        initializationListeners()
    }

    private fun initializationViewModel() {
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
    }

    private fun observeNews() {
        viewModel.news.observe(viewLifecycleOwner,
            { articles ->
                list.addAll(articles!!)
                newsAdapter.notifyDataSetChanged()
            })
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
    }

    private fun requestInAPI() {
        viewModel.requestInAPI()
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
}