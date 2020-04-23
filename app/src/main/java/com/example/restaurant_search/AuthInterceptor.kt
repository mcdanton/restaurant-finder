package com.example.restaurant_search

import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor : Interceptor {

    private val accessToken: String = "15X5giOddJN8QEv5mDBI4_6jlYsG4sW4I5i4dQ2T7t9MwyM-KBBaz8BogPZyFMegAC2DwKJRxrTOKNJBWn2Pgjpo6u5AWDvBXOO5pWL1PFvwPuqyvd9a0zsHRmagXnYx"

    override fun intercept(chain: Interceptor.Chain): Response {

        val request = chain.request().newBuilder()
            .addHeader("Authorization", "Bearer $accessToken")
            .build()

        return chain.proceed(request)

    }

}