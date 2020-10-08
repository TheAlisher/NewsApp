package com.alis.news.data

import android.content.Context
import android.content.SharedPreferences
import com.alis.news.App

private const val PREF_ENDPOINTS = "endpoints"

class AppPreferences(context: Context) {

    private var preferences: SharedPreferences =
        context.getSharedPreferences("lotcion_preferences", Context.MODE_PRIVATE)

    fun endpoint(): Boolean? {
        return preferences.getBoolean(PREF_ENDPOINTS, true)
    }

    fun setEndpoint(endpoint: Boolean) {
        preferences.edit().putBoolean(PREF_ENDPOINTS, endpoint).apply()
    }
}