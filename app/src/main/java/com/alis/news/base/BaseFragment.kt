package com.alis.news.base

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment

open class BaseFragment : Fragment() {

    protected var hasInitializedRootView = false
    private var rootView: View? = null

    fun getPersistentViews(
        inflater: LayoutInflater,
        container: ViewGroup?,
        layoutID: Int
    ): View? {
        if (rootView == null) {
            rootView = inflater.inflate(layoutID, container, false)
        } else {
            (rootView?.parent as? ViewGroup)?.removeView(rootView)
        }
        return rootView
    }
}