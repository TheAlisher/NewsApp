package com.alis.news.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.alis.news.R
import com.alis.news.models.NewsResponse

class NewsAdapter : RecyclerView.Adapter<NewsAdapter.NewsViewHolder>() {

    var list = listOf<NewsResponse>()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        return NewsViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.list_news_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        holder.onBind(list[position])
    }

    override fun getItemCount(): Int {
        return list.size
    }


    class NewsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun onBind(newsResponse: NewsResponse) {

        }
    }
}