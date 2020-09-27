package com.alis.news.db.converter

import androidx.room.TypeConverter
import com.alis.news.models.NewsSource
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class SourceConverter {

    @TypeConverter
    fun toRaw(questions: List<NewsSource?>?): String? {
        if (questions == null) return null
        val gson = Gson()
        val type = object : TypeToken<List<NewsSource?>?>() {}.type
        return gson.toJson(questions, type)
    }

    @TypeConverter
    fun fromRaw(raw: String?): List<NewsSource?>? {
        if (raw == null) return null
        val gson = Gson()
        val type = object : TypeToken<List<NewsSource?>?>() {}.type
        return gson.fromJson<List<NewsSource?>>(raw, type)
    }
}