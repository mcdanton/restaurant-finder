package com.example.restaurant_search

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.RecyclerView
import com.apollographql.apollo.ApolloCall
import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.api.Response
import com.apollographql.apollo.exception.ApolloException
import com.example.SearchYelpQuery
import com.example.restaurant_search.models.Restaurant
import okhttp3.OkHttpClient

class MainActivity : AppCompatActivity() {

    private lateinit var searchYelpQuery: SearchYelpQuery

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val recyclerView = RecyclerView(this)
        recyclerView.adapter = RestaurantListAdapter(listOf(Restaurant("Burrito King",
        "45 West Street", "", "")))

        yelpRequest()
    }

    fun yelpRequest() {
        val url = "https://api.yelp.com/v3/graphql"

        searchYelpQuery = SearchYelpQuery.builder().build()


        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(AuthInterceptor())
            .build()


        val apolloClient = ApolloClient.builder()
            .serverUrl(url)
            .okHttpClient(okHttpClient)
            .build()

        apolloClient.query(searchYelpQuery)?.enqueue(object : ApolloCall.Callback<SearchYelpQuery.Data>() {

            override fun onResponse(response: Response<SearchYelpQuery.Data>) {
                Log.d("", "${response.data().toString()}")
            }

            override fun onFailure(e: ApolloException) {
                Log.d("", "${e.localizedMessage.toString()}")

            }

        })

    }
}
