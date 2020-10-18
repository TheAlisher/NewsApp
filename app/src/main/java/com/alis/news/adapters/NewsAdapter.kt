package com.alis.news.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.alis.news.R
import com.alis.news.extension.loadImage
import com.alis.news.models.NewsArticles
import kotlinx.android.synthetic.main.item_news.view.*

class NewsAdapter(
    private var list: MutableList<NewsArticles>,
) : RecyclerView.Adapter<NewsAdapter.NewsViewHolder>() {

    private lateinit var listener: OnItemClickListener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        return NewsViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_news, parent, false)
        )
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        holder.onBind(list[position])

        holder.itemView.setOnClickListener {
            listener.onNewsItemClick(list[position])
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    fun addAll(list: List<NewsArticles>) {
        this.list.addAll(list)
        notifyDataSetChanged()
    }

    fun clear() {
        this.list.clear()
        notifyDataSetChanged()
    }

    fun setOnItemClickListener(onItemClickListener: OnItemClickListener) {
        this.listener = onItemClickListener
    }

    class NewsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun onBind(newsArticles: NewsArticles) {
            itemView.image_news.loadImage(newsArticles.urlToImage)
            itemView.text_news_title.text = newsArticles.title
            itemView.text_news_description.text = newsArticles.description
        }
    }

    interface OnItemClickListener {
        fun onNewsItemClick(item: NewsArticles)
    }
}