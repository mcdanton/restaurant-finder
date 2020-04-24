package com.example.restaurant_search

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.apollographql.apollo.ApolloCall
import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.api.Response
import com.apollographql.apollo.exception.ApolloException
import com.example.SearchYelpResQuery
import com.example.restaurant_search.models.Restaurant
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient


class RestaurantListFragment : Fragment() {

    private lateinit var searchYelpResQuery:SearchYelpResQuery
    private var restaurants = mutableListOf<Restaurant>()


    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        yelpRequest()

        val view = inflater.inflate(R.layout.fragment_restaurant_list, container, false)

        val recyclerView = view.findViewById<RecyclerView>(R.id.restaurant_list_recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(activity!!)
        recyclerView.adapter = RestaurantListAdapter(restaurants)
        return view

    }

    init {
        restaurants = mutableListOf(
            Restaurant("Burrito King", "2779 Mission St, San Francisco, CA ", "$", "7189743574"),
            Restaurant("Bobby's Burritos", "841 Derby Lane, San Francisco, CA ", "$$", "2128394823"),
            Restaurant("Burritos and More", "142 Oakwood St, San Francisco, C", "$", "5163824204"),
            Restaurant("Classic Burritos", "2889 Mission St, San Francisco, C", "$$$", "4382938573"),
            Restaurant("BBQ, Burritos, and Golf", "2889 Mission St, San Francisco, C", "$$", "3249837520")
        )


    }

    fun yelpRequest() {
        val url = "https://api.yelp.com/v3/graphql"

        searchYelpResQuery = SearchYelpResQuery.builder().build()


        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(AuthInterceptor())
            .build()


        val apolloClient = ApolloClient.builder()
            .serverUrl(url)
            .okHttpClient(okHttpClient)
            .build()

        apolloClient.query(searchYelpResQuery)?.enqueue(object : ApolloCall.Callback<SearchYelpResQuery.Data>() {

            override fun onResponse(response: Response<SearchYelpResQuery.Data>) {

                val jsonString = Gson().toJson(response)
                val foo = Gson().fromJson(jsonString, Restaurant::class.java)
//                val gson = GsonBuilder().create()
//                val restaurant = gson.fromJson(response, Restaurant::class.java)

                Log.d("", "${response.data().toString()}")
//                Log.d("", "restaurant is ${restaurant}")

            }

            override fun onFailure(e: ApolloException) {
                Log.d("", "${e.localizedMessage.toString()}")

            }

        })

    }

}