package com.example.restaurant_search.views

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.restaurant_search.R
import com.example.restaurant_search.view_models.NavigationViewModel


class MainActivity : AppCompatActivity() {

    private lateinit var navigationViewModel: NavigationViewModel
    private val restaurantListFragment by lazy { RestaurantListFragment() }
    private val restaurantMapFragment by lazy { RestaurantMapFragment() }

    //region Lifecycle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            navigationViewModel = ViewModelProvider(this).get(NavigationViewModel::class.java)

            // Allows fragments to easily pass in fragment id for
            // Main Activity to access
            navigationViewModel.fragmentId.observe(this, Observer {
                replaceFragment(it, navigationViewModel.selectedRestaurant?.name)
            })
        }

    }

    //endregion Lifecycle

    //region Event Handling

    // Handles back button from RestaurantMapFragment
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            navigationViewModel.updateFragment(R.layout.fragment_restaurant_list)
            return true
        }

        return super.onOptionsItemSelected(item)
    }

    // Only handle back press from MapFrag
    override fun onBackPressed() {
        if (navigationViewModel.fragmentId.value == R.layout.fragment_restaurant_map) {
            navigationViewModel.updateFragment(R.layout.fragment_restaurant_list)
            super.onBackPressed()
        }
    }

    private fun replaceFragment(fragmentId: Int?, fragmentTitle: String? = null) {

        // Include "extra" case here for safety in case unsupported Frag Id passed
        val fragment = when(fragmentId) {
            R.layout.fragment_restaurant_map -> {
                supportActionBar?.setDisplayHomeAsUpEnabled(true)
                restaurantMapFragment
            }
            R.layout.fragment_restaurant_list -> {
                supportActionBar?.setDisplayHomeAsUpEnabled(false)
                restaurantListFragment
            }
            else -> {
                supportActionBar?.setDisplayHomeAsUpEnabled(false)
                restaurantListFragment
            }
        }

        supportFragmentManager
            .beginTransaction().apply {
                replace(R.id.main, fragment)
                title = fragmentTitle ?: getString(R.string.app_name)
                addToBackStack(this::class.java.simpleName)
                commit()
            }
    }

    //endregion Event Handling

    //region Helpers

    private fun fragFromId(fragId: Int?) : Fragment {

        return when (fragId) {
            R.layout.fragment_restaurant_list -> restaurantListFragment
            R.layout.fragment_restaurant_map -> restaurantMapFragment
            else -> restaurantListFragment
        }

    }

    //endregion Helpers

}
