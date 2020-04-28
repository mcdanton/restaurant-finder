package com.example.restaurant_search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModelProvider
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


    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        binding = FragmentRestaurantMapBinding.inflate(inflater, container, false)

        binding.lifecycleOwner = this
        binding.vm = viewModel

        return binding.root

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val fm: FragmentManager = childFragmentManager
        supportMapFragment = fm.findFragmentById(R.id.map) as SupportMapFragment
        if (supportMapFragment == null) {
            supportMapFragment = SupportMapFragment.newInstance()
            fm.beginTransaction().replace(R.id.main, supportMapFragment).commit()
        }

        supportMapFragment.getMapAsync(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        navigationViewModel = activity?.run {
            ViewModelProvider(this).get(NavigationViewModel::class.java)
        } ?: throw Exception("Invalid Activity")

        viewModel = RestaurantViewModel(navigationViewModel.selectedRestaurant!!)

    }


    override fun onMapReady(map: GoogleMap) {

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

}