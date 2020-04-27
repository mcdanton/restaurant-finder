package com.example.restaurant_search

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class MainActivity : AppCompatActivity() {

    private val restaurantListFragment by lazy { RestaurantListFragment() }
    private val restaurantMapFragment by lazy { RestaurantMapFragment() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
//            supportFragmentManager
//                .beginTransaction()
//                .add(R.id.main, restaurantListFragment, "restaurantList")
//                .commit()

            supportFragmentManager
                .beginTransaction()
                .add(R.id.main, restaurantMapFragment, "restaurantMap")
                .commit()
        }


    }

}
