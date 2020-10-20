package com.alis.news.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.alis.news.R
import com.alis.news.extension.loadCenterCropRoundImage
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
        val item: NewsArticles = list[position]
        setUpListeners(holder, item)
    }

    private fun setUpListeners(holder: NewsViewHolder, item: NewsArticles) {
        clickNews(holder, item)
        clickNewsLike(holder, item)
    }

    private fun clickNews(holder: NewsViewHolder, item: NewsArticles) {
        holder.itemView.setOnClickListener {
            listener.onNewsItemClick(item)
        }
    }

    private fun clickNewsLike(holder: NewsViewHolder, item: NewsArticles) {
        val imageLike = holder.itemView.image_news_like
        holder.itemView.image_news_like.setOnClickListener {
            listener.onNewsItemLikeClick(item)
            if (item.isFavorite) {
                imageLike.setImageResource(R.drawable.icon_favorite)
            } else {
                imageLike.setImageResource(R.drawable.icon_favorite_border)
            }
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

    class NewsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun onBind(newsArticles: NewsArticles) {
            itemView.image_news.loadCenterCropRoundImage(
                newsArticles.urlToImage,
                roundedRadius = 20
            )
            itemView.text_news_title.text = newsArticles.title
            itemView.text_news_description.text = newsArticles.description

            if (newsArticles.isFavorite) {
                itemView.image_news_like.setImageResource(R.drawable.icon_favorite)
            } else {
                itemView.image_news_like.setImageResource(R.drawable.icon_favorite_border)
            }
        }
    }

    fun setOnItemClickListener(onItemClickListener: OnItemClickListener) {
        this.listener = onItemClickListener
    }

    interface OnItemClickListener {
        fun onNewsItemClick(item: NewsArticles)
        fun onNewsItemLikeClick(item: NewsArticles)
    }
}