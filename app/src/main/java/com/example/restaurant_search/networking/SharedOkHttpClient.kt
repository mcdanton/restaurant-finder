package com.example.restaurant_search.networking

import okhttp3.OkHttpClient

object SharedOkHttpClient {

    val singleton = OkHttpClient.Builder()
        .addInterceptor(AuthInterceptor())
        .build()
}