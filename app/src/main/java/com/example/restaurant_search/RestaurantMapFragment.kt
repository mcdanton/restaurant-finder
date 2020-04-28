package com.example.restaurant_search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModelProvider
import com.example.restaurant_search.view_models.NavigationViewModel
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions


class RestaurantMapFragment: Fragment(), OnMapReadyCallback {

    private lateinit var supportMapFragment: SupportMapFragment
    private lateinit var navigationViewModel: NavigationViewModel


    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        val view =  inflater.inflate(R.layout.fragment_restaurant_map, container, false)
        return view

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val fm: FragmentManager = childFragmentManager
        var fragment = fm.findFragmentById(R.id.map) as SupportMapFragment
        if (fragment == null) {
            fragment = SupportMapFragment.newInstance()
            fm.beginTransaction().replace(R.id.main, fragment).commit()
        }

        fragment.getMapAsync(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        navigationViewModel = activity?.run {
            ViewModelProvider(this).get(NavigationViewModel::class.java)
        } ?: throw Exception("Invalid Activity")

    }


    override fun onMapReady(map: GoogleMap) {
        val restaurantCoords = navigationViewModel.selectedRestaurant?.coordinates
        if (restaurantCoords?.longitude != null && restaurantCoords?.latitude != null) {
            val longLat = LatLng(restaurantCoords!!.latitude!!, restaurantCoords!!.longitude!!)
            map.addMarker(
                MarkerOptions().position(longLat)
                    .title(navigationViewModel.selectedRestaurant?.name)
            )
            val zoomLevel = 17.0f //This goes up to 21
            map.moveCamera(CameraUpdateFactory.newLatLngZoom(longLat, zoomLevel))
        }
    }

}