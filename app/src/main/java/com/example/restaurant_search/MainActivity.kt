package com.example.restaurant_search

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.os.Bundle
import android.os.Looper
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.restaurant_search.view_models.NavigationViewModel
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices


class MainActivity : AppCompatActivity() {

    private lateinit var navigationViewModel: NavigationViewModel
    private val restaurantListFragment by lazy { RestaurantListFragment() }
    private val restaurantMapFragment by lazy { RestaurantMapFragment() }

//    val PERMISSION_ID = 44


    //region Lifecycle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {

            navigationViewModel = ViewModelProvider(this).get(NavigationViewModel::class.java)

            navigationViewModel.fragmentId.observe(this, Observer {
                replaceFragment(it, navigationViewModel.selectedRestaurant?.name)
            })

        }

    }

    //endregion Lifecycle

    //region Event Handling

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item?.itemId == android.R.id.home) {
            navigationViewModel.updateFragment(R.layout.fragment_restaurant_list, null)
            return true
        }

        return super.onOptionsItemSelected(item)
    }

    override fun onBackPressed() {
        if (navigationViewModel.fragmentId.value == R.layout.fragment_restaurant_map) {
            navigationViewModel.updateFragment(R.layout.fragment_restaurant_list, null)
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

        supportFragmentManager.popBackStack(this::class.java.simpleName, 0)
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

    //region Location Management

    /*
    private fun checkPermissions(): Boolean {
        return ActivityCompat.checkSelfPermission(
            this,
            Manifest.permission.ACCESS_COARSE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(
                    this,
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) == PackageManager.PERMISSION_GRANTED
    }

    private fun requestPermissions() {
        ActivityCompat.requestPermissions(
            this,
            arrayOf(
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION
            ),
            PERMISSION_ID
        )
    }

    private fun isLocationEnabled(): Boolean {
        val locationManager =
            getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) ||
                locationManager.isProviderEnabled(
                    LocationManager.NETWORK_PROVIDER
                )
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == PERMISSION_ID) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Granted. Start getting the location information
            }
        }
    }

    @SuppressLint("MissingPermission")
    private fun getLastLocation() {
        if (checkPermissions()) {
            if (isLocationEnabled()) {
                fusedLocationClient.lastLocation.addOnCompleteListener { task ->
                    val location: Location? = task.result
                    if (location == null) {
                        requestNewLocationData()
                    } else {
//                        latTextView.setText(location.getLatitude().toString() + "")
//                        lonTextView.setText(location.getLongitude().toString() + "")
                    }
                }
            } else {
                Toast.makeText(this, "Turn on location", Toast.LENGTH_LONG).show()
                val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
                startActivity(intent)
            }
        } else {
            requestPermissions()
        }
    }

    @SuppressLint("MissingPermission")
    private fun requestNewLocationData() {
        val locationRequest = LocationRequest()
        locationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        locationRequest.interval = 0
        locationRequest.fastestInterval = 0
        locationRequest.numUpdates = 1
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        mFusedLocationClient.requestLocationUpdates(
            locationRequest, mLocationCallback,
            Looper.myLooper()
        )
    }
    */

    //endregion Location Management

}
