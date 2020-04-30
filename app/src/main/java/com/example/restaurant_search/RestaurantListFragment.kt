package com.example.restaurant_search

import android.Manifest
import android.content.DialogInterface
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.eazypermissions.common.model.PermissionResult
import com.eazypermissions.dsl.extension.requestPermissions
import com.example.SearchYelpResQuery
import com.example.restaurant_search.view_models.NavigationViewModel
import com.example.restaurant_search.view_models.RestaurantListViewModel
import com.google.android.gms.location.FusedLocationProviderClient
import kotlinx.android.synthetic.main.fragment_restaurant_list.*


class RestaurantListFragment : Fragment() {

    private lateinit var viewModel: RestaurantListViewModel
    private lateinit var navigationViewModel: NavigationViewModel
    private lateinit var recyclerView: RecyclerView
    private var adapter: RestaurantListAdapter? = null
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private val defaultLat: Double = 40.7484
    private val defaultLong: Double = -73.9857

    //region Lifecycle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        navigationViewModel = activity?.run {
            ViewModelProvider(this).get(NavigationViewModel::class.java)
        } ?: throw Exception("Invalid Activity")

    }

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        val view = inflater.inflate(R.layout.fragment_restaurant_list, container, false)

        recyclerView = view.findViewById<RecyclerView>(R.id.restaurant_list_recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(activity!!)
        recyclerView.adapter = RestaurantListAdapter(mutableListOf())

        return view

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = RestaurantListViewModel()

        fusedLocationClient = FusedLocationProviderClient(activity!!)

        if (!haveCheckedPermissions()) {
            AlertDialog.Builder(activity!!).apply {
                setMessage(R.string.permission_needed_dialog_message)
                setPositiveButton(
                    R.string.dialog_confirmation,
                    DialogInterface.OnClickListener { dialog, _ ->
                        handlePermissions()
                        dialog.dismiss()
                    })

                create().show()
            }
        } else {
            fusedLocationClient.lastLocation
                .addOnSuccessListener { location : Location? ->
                    navigationViewModel.userLocation = location

                    Log.d("", "#### lat: ${navigationViewModel.userLocation?.latitude}, long: ${navigationViewModel.userLocation?.longitude}")

                    viewModel.fetchBurritoRestaurants(getString(R.string.yelp_search_term),
                        navigationViewModel.userLocation?.latitude ?: defaultLat,
                        navigationViewModel.userLocation?.longitude ?: defaultLong)
                }
        }


        viewModel.restaurants.observe(viewLifecycleOwner, Observer {

            activity!!.runOnUiThread {

                Log.d("User Location", "User Location is lat: ${navigationViewModel.userLocation?.latitude}, long: ${navigationViewModel.userLocation?.longitude}")

                recyclerView.adapter = RestaurantListAdapter(it, ::showRestaurantMap)
                adapter?.notifyDataSetChanged()

                if(it.isNullOrEmpty())
                    layout_no_restaurants_found.visibility = View.VISIBLE
                else
                    layout_no_restaurants_found.visibility = View.GONE

            }

        })

        viewModel.error.observe(viewLifecycleOwner, Observer {

            activity!!.runOnUiThread {

                val dialogBuilder = AlertDialog.Builder(activity!!)
                dialogBuilder.setMessage(R.string.apollo_error_dialog_message)
                dialogBuilder.setPositiveButton(
                    R.string.dialog_confirmation,
                    DialogInterface.OnClickListener { dialog, _ ->
                        dialog.dismiss()
                    })

                dialogBuilder.create().show()
            }
        })
    }

    //endregion Lifecycle

    //region Adapter Interaction

    private fun showRestaurantMap(item: SearchYelpResQuery.Business?) {

        // Ensure item is not null (shouldn't be possible but always good to handle just in case)
        // If long/lat is null, show error to user
        item?.let {
            if (item.coordinates?.longitude == null || item.coordinates?.latitude == null ) {

                val dialogBuilder = AlertDialog.Builder(activity!!)
                dialogBuilder.setMessage(R.string.restaurant_details_dialog_message)
                dialogBuilder.setPositiveButton(
                    R.string.dialog_confirmation,
                    DialogInterface.OnClickListener { dialog, _ ->
                        dialog.dismiss()
                    })

                dialogBuilder.create().show()
                return
            }

            navigationViewModel.updateFragment(R.layout.fragment_restaurant_map, item)

        }

    }

    //endregion Adapter Interaction

    private fun handlePermissions() {
        val alertDialog = AlertDialog.Builder(activity!!).apply {
            setMessage(R.string.permission_needed_dialog_message)
        }

        requestPermissions(
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION
        ) {
            requestCode = 44
            resultCallback = {
                when(this) {
                    is PermissionResult.PermissionGranted -> {
                        Log.d("Location_Permissions", "Location permission was granted")

                        fusedLocationClient.lastLocation
                            .addOnSuccessListener { location : Location? ->
                                navigationViewModel.userLocation = location

                                viewModel.fetchBurritoRestaurants(getString(R.string.yelp_search_term),
                                    navigationViewModel.userLocation?.latitude ?: defaultLat,
                                    navigationViewModel.userLocation?.longitude ?: defaultLong)
                            }
                    }
                    is PermissionResult.PermissionDenied -> {
                        Log.d("Location_Permissions", "Location permission was denied")
                        alertDialog.create().show()
                    }
                    is PermissionResult.PermissionDeniedPermanently -> {
                        Log.d("Location_Permissions", "Location permission was permanently denied")
                        alertDialog.create().show()
                    }
                    is PermissionResult.ShowRational -> {
                        Log.d("Location_Permissions", "Location permission was manually denied")
                        alertDialog.create().show()
                    }
                }
            }
        }
    }

    private fun haveCheckedPermissions(): Boolean {
        return ActivityCompat.checkSelfPermission(
            activity!!,
            Manifest.permission.ACCESS_COARSE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(
                    activity!!,
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) == PackageManager.PERMISSION_GRANTED
    }


}
