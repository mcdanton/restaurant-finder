package com.example.restaurant_search.view_models

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.apollographql.apollo.ApolloCall
import com.apollographql.apollo.api.Response
import com.apollographql.apollo.exception.ApolloException
import com.example.SearchYelpResQuery
import com.example.restaurant_search.shared.SharedApolloClient

class RestaurantListViewModel() : ViewModel() {

    val restaurants: MutableLiveData<MutableList<SearchYelpResQuery.Business?>> = MutableLiveData()
    val error = MutableLiveData<Exception>()

    fun fetchBurritoRestaurants(restaurantType: String, searchLatitude: Double, searchLongitude: Double) {

        val apolloClient = SharedApolloClient.singleton

        apolloClient.query(SearchYelpResQuery(restaurantType, searchLatitude, searchLongitude))?.enqueue(object : ApolloCall.Callback<SearchYelpResQuery.Data>() {

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
