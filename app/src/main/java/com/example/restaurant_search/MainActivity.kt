package com.example.restaurant_search

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.restaurant_search.view_models.NavigationViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var navigationViewModel: NavigationViewModel
    private val restaurantListFragment by lazy { RestaurantListFragment() }
    private val restaurantMapFragment by lazy { RestaurantMapFragment() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {

            navigationViewModel = ViewModelProvider(this).get(NavigationViewModel::class.java)


            navigationViewModel.fragmentId.observe(this, Observer {
                replaceFragment(
                    when(it) {
                        R.layout.fragment_restaurant_map -> restaurantMapFragment
                        else -> restaurantListFragment
                    }
                )
            })
        }

    }

    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.main, fragment)
            .commit()
    }

}
