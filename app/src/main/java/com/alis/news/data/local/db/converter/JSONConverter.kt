package com.alis.news.data.local.db.converter

import androidx.room.TypeConverter
import com.alis.news.models.NewsSource
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class JSONConverter {

    @TypeConverter
    fun toRaw(questions: NewsSource?): String? {
        if (questions == null) return null
        val gson = Gson()
        val type = object : TypeToken<NewsSource?>() {}.type
        return gson.toJson(questions, type)
    }

    @TypeConverter
    fun fromRaw(raw: String?): NewsSource? {
        if (raw == null) return null
        val gson = Gson()
        val type = object : TypeToken<NewsSource?>() {}.type
        return gson.fromJson<NewsSource>(raw, type)
    }
}