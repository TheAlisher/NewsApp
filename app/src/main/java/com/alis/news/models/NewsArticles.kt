package com.alis.news.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.alis.news.data.local.db.converter.SourceConverter
import java.io.Serializable

@Entity
@TypeConverters(SourceConverter::class)
data class NewsArticles(
    @PrimaryKey(autoGenerate = true)
    var id: Int,
    var source: NewsSource? = null,
    var author: String? = null,
    var title: String? = null,
    var description: String? = null,
    var url: String? = null,
    var urlToImage: String? = null,
    var publishedAt: String? = null,
    var content: String? = null,
    var isFavorite: Boolean = false
)