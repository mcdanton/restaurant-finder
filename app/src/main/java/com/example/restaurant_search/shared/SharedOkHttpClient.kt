package com.example.restaurant_search.shared

import com.example.restaurant_search.AuthInterceptor
import okhttp3.OkHttpClient

object SharedOkHttpClient {

    val singleton = OkHttpClient.Builder()
        .addInterceptor(AuthInterceptor())
        .build()
}