package com.alis.news.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment

abstract class BaseFragment<ViewModel : BaseViewModel>(private val layoutID: Int) : Fragment() {

    protected abstract val viewModel: ViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)
        return inflater.inflate(layoutID, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initializeViews()
        setUpListeners()
        observe()
    }

    abstract fun initializeViews()

    abstract fun setUpListeners()

    abstract fun observe()
}