package com.example.restaurant_search.shared

import com.apollographql.apollo.ApolloClient


object SharedApolloClient {

    private val url = "https://api.yelp.com/v3/graphql"
    private val okHttpClient = SharedOkHttpClient.singleton

    val singleton = ApolloClient.builder().serverUrl(url).okHttpClient(okHttpClient).build()

}
