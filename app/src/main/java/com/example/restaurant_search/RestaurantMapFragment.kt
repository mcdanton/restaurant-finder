package com.example.restaurant_search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions


class RestaurantMapFragment: Fragment(), OnMapReadyCallback {

    private lateinit var supportMapFragment: SupportMapFragment

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


    override fun onMapReady(map: GoogleMap) {
        // Add a marker in Sydney, Australia,
        // and move the map's camera to the same location.
        // Add a marker in Sydney, Australia,
        // and move the map's camera to the same location.
        val sydney = LatLng(-33.852, 151.211)
        map.addMarker(
            MarkerOptions().position(sydney)
                .title("Marker in Sydney")
        )
        val zoomLevel = 16.0f //This goes up to 21
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney, zoomLevel))
    }

}