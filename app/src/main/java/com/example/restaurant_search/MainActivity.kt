package com.example.restaurant_search

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.RecyclerView
import com.apollographql.apollo.ApolloCall
import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.api.Response
import com.apollographql.apollo.exception.ApolloException
import com.example.restaurant_search.models.Restaurant
import okhttp3.OkHttpClient

class MainActivity : AppCompatActivity() {

    private val restaurantListFragment by lazy { RestaurantListFragment() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            supportFragmentManager
                .beginTransaction()
                .add(R.id.main, restaurantListFragment, "restaurantList")
                .commit()
        }


    }

}
