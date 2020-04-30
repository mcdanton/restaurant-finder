package com.example.restaurant_search.views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModelProvider
import com.example.restaurant_search.R
import com.example.restaurant_search.databinding.FragmentRestaurantMapBinding
import com.example.restaurant_search.view_models.NavigationViewModel
import com.example.restaurant_search.view_models.RestaurantViewModel
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions


class RestaurantMapFragment: Fragment(), OnMapReadyCallback {

    private lateinit var binding: FragmentRestaurantMapBinding
    private lateinit var supportMapFragment: SupportMapFragment
    private lateinit var viewModel: RestaurantViewModel
    private lateinit var navigationViewModel: NavigationViewModel

    //region Lifecycle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        navigationViewModel = activity?.run {
            ViewModelProvider(this).get(NavigationViewModel::class.java)
        } ?: throw Exception("Invalid Activity")

        // Ensured restaurant can't be null in previous fragment
        viewModel = RestaurantViewModel(navigationViewModel.selectedRestaurant!!)

    }

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        // Used binding here. Would likely update List Frag to use binding too in future.
        binding = FragmentRestaurantMapBinding.inflate(inflater, container, false)

        binding.lifecycleOwner = this
        binding.vm = viewModel

        return binding.root

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val fragmentManager = childFragmentManager
        supportMapFragment = fragmentManager.findFragmentById(R.id.map) as SupportMapFragment

        supportMapFragment.getMapAsync(this)

    }

    //endregion Lifecycle

    //region MapSetup

    override fun onMapReady(map: GoogleMap) {

        // Don't really need null checks but with async call it's always safer
        navigationViewModel.selectedRestaurant?.let {

            if (it.latitude != null && it.longitude != null) {
                val longLat = LatLng(it.latitude, it.longitude)
                map.addMarker(
                    MarkerOptions().position(longLat)
                        .title(it.name)
                )
                val zoomLevel = 17.0f //This goes up to 21
                map.moveCamera(CameraUpdateFactory.newLatLngZoom(longLat, zoomLevel))

            }
        }

    }

    //endregion MapSetup

}