package com.example.restaurant_search

import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.SearchYelpResQuery
import com.example.restaurant_search.view_models.NavigationViewModel
import com.example.restaurant_search.view_models.RestaurantListViewModel


class RestaurantListFragment : Fragment() {

    private lateinit var viewModel: RestaurantListViewModel
    private lateinit var navigationViewModel: NavigationViewModel
    private lateinit var recyclerView: RecyclerView
    private var adapter: RestaurantListAdapter? = null


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
        viewModel.fetchBurritoRestaurants()

        viewModel.restaurants.observe(viewLifecycleOwner, Observer {

            activity!!.runOnUiThread {
                recyclerView.adapter = RestaurantListAdapter(it, ::showRestaurantMap)
                adapter?.notifyDataSetChanged()
            }

        })
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        navigationViewModel = activity?.run {
            ViewModelProvider(this).get(NavigationViewModel::class.java)
        } ?: throw Exception("Invalid Activity")

    }

    private fun showRestaurantMap(item: SearchYelpResQuery.Business?) {

        // If long/lat is null, show error to user
        item?.let {
            if (item?.coordinates?.longitude == null || item?.coordinates?.latitude == null ) {

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
        }

        navigationViewModel.updateFragment(R.layout.fragment_restaurant_map, item)
    }

}
