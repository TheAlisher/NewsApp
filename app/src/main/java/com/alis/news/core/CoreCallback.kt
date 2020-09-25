package com.alis.news.core

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

abstract class CoreCallback<T> : Callback<T> {

    abstract fun onSuccess(result: T)
    abstract fun onFailure(exception: Exception)

    override fun onResponse(call: Call<T>, response: Response<T>) {
        if (response.isSuccessful) {
            if (response.body() != null) {
                onSuccess(result = response.body()!!)
            } else {
                onFailure(Exception("Body is empty"))
            }
        } else {
            onFailure(Exception("Response code: " + response.code()))
        }
    }

    override fun onFailure(call: Call<T>, t: Throwable) {
        onFailure(Exception(t))
    }
}