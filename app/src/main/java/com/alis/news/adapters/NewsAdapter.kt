package com.alis.news.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.alis.news.R
import com.alis.news.models.NewsArticles
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.list_news_item.view.*

class NewsAdapter(
    private var list: ArrayList<NewsArticles>,
) :
    RecyclerView.Adapter<NewsAdapter.NewsViewHolder>() {

    private lateinit var listener: OnItemClickListener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        return NewsViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.list_news_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        holder.onBind(list[position], listener)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    fun setOnItemClickListener(onItemClickListener: OnItemClickListener) {
        this.listener = onItemClickListener
    }

    class NewsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun onBind(newsArticles: NewsArticles, listener: OnItemClickListener) {
            Glide
                .with(itemView.image_news.context)
                .load(newsArticles.urlToImage)
                .into(itemView.image_news)
            itemView.text_news_title.text = newsArticles.title
            itemView.text_news_description.text = newsArticles.description

            initializationListeners(listener)
        }

        private fun initializationListeners(listener: OnItemClickListener) {
            itemView.setOnClickListener {
                listener.onNewsItemClick(adapterPosition)
            }
        }
    }

    interface OnItemClickListener {
        fun onNewsItemClick(position: Int)
    }
}