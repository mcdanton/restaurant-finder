package com.example.restaurant_search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.restaurant_search.models.Restaurant


class RestaurantListFragment : Fragment() {

    companion object {

        fun newInstance(): RestaurantListFragment {
            return RestaurantListFragment()
        }
    }

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        val view = inflater.inflate(R.layout.fragment_restaurant_list, container, false)

        val recyclerView = view.findViewById<RecyclerView>(R.id.restaurant_list_recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(activity!!)
        recyclerView.adapter = RestaurantListAdapter(mutableListOf(Restaurant("Bones", "adsf", "af", "")))
        return view

    }

}