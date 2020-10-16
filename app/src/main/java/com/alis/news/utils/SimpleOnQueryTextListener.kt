package com.alis.news.utils

import android.widget.SearchView

open class SimpleOnQueryTextListener : SearchView.OnQueryTextListener {

    override fun onQueryTextSubmit(query: String?): Boolean {
        return false
    }

    override fun onQueryTextChange(query: String?): Boolean {
        return false
    }
}