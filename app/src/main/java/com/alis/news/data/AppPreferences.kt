package com.alis.news.data

import android.content.Context
import android.content.SharedPreferences
import com.alis.news.App

class AppPreferences(context: Context) {

    private var preferences: SharedPreferences =
        context.getSharedPreferences("news_preferences", Context.MODE_PRIVATE)
}