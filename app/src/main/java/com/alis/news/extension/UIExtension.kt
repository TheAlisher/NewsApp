package com.alis.news.extension

import android.content.Context
import android.widget.Toast

fun showToastSHORT(context: Context, message: String) {
    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
}

fun showToastLONG(context: Context, message: String) {
    Toast.makeText(context, message, Toast.LENGTH_LONG).show()
}