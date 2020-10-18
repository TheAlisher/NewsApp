package com.alis.news.extension

import android.content.Context
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners

fun showToastShort(context: Context, message: String) {
    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
}

fun showToastLong(context: Context, message: String) {
    Toast.makeText(context, message, Toast.LENGTH_LONG).show()
}

fun showToastShort(context: Context, message: Int) {
    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
}

fun showToastLong(context: Context, message: Int) {
    Toast.makeText(context, message, Toast.LENGTH_LONG).show()
}

fun View.visible() {
    visibility = View.VISIBLE
}

fun View.invisible() {
    visibility = View.INVISIBLE
}

fun View.gone() {
    visibility = View.GONE
}

fun SwipeRefreshLayout.startRefresh() {
    isRefreshing = true
}

fun SwipeRefreshLayout.stopRefresh() {
    isRefreshing = false
}

fun ImageView.loadImage(url: String? = null, placeholder: Int = 0) {
    Glide.with(context)
        .load(url)
        .placeholder(placeholder)
        .into(this)
}

fun ImageView.loadCenterCropRoundImage(
    url: String? = null,
    placeholder: Int = 0,
    roundedRadius: Int
) {
    Glide.with(context)
        .load(url)
        .placeholder(placeholder)
        .transform(CenterCrop(), RoundedCorners(roundedRadius))
        .into(this)
}