package com.example.restaurant_search.view_models

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.SearchYelpResQuery
import com.example.restaurant_search.R
import com.example.restaurant_search.default

class NavigationViewModel : ViewModel() {

    val fragmentId = MutableLiveData<Int>().default(R.layout.fragment_restaurant_list)
    var selectedRestaurant: SearchYelpResQuery.Business? = null


    fun updateFragment(id: Int, restaurant: SearchYelpResQuery.Business?) {
        selectedRestaurant = restaurant
        fragmentId.postValue(id)

    }

}