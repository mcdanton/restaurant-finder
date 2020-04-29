package com.example.restaurant_search.view_models

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.apollographql.apollo.ApolloCall
import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.api.Response
import com.apollographql.apollo.exception.ApolloException
import com.example.SearchYelpResQuery
import com.example.restaurant_search.AuthInterceptor
import okhttp3.OkHttpClient

class RestaurantListViewModel() : ViewModel() {

    var restaurants: MutableLiveData<MutableList<SearchYelpResQuery.Business?>> = MutableLiveData()

    fun fetchBurritoRestaurants() {
        val url = "https://api.yelp.com/v3/graphql"

        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(AuthInterceptor())
            .build()

        val apolloClient = ApolloClient.builder()
            .serverUrl(url)
            .okHttpClient(okHttpClient)
            .build()

        apolloClient.query(SearchYelpResQuery("burrito", 40.6178292, -73.7316214))?.enqueue(object : ApolloCall.Callback<SearchYelpResQuery.Data>() {

            override fun onResponse(response: Response<SearchYelpResQuery.Data>) {

                // Only checking for null and not empty here
                // If response is empty and current list of restaurants is not
                // want to update current list to be empty
                if (response.data()?.search?.business != null) {
                    restaurants.postValue(response.data()!!.search!!.business!!.filter { it?.name != null }.toMutableList())
                }

            }

            override fun onFailure(e: ApolloException) {
                Log.d("", "${e.localizedMessage.toString()}")

            }

        })

    }
}