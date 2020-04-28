package com.example.restaurant_search.view_models

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.SearchYelpResQuery
import com.example.restaurant_search.R
import com.example.restaurant_search.default
import com.example.restaurant_search.models.Restaurant

class NavigationViewModel : ViewModel() {

    val fragmentId = MutableLiveData<Int>().default(R.layout.fragment_restaurant_list)
    var selectedRestaurant: Restaurant? = null


    fun updateFragment(id: Int, restaurant: SearchYelpResQuery.Business?) {

        restaurant?.let {
            selectedRestaurant = Restaurant(
                name = restaurant.name,
                address = restaurant.location?.address1,
                priceRange = restaurant.price,
                phoneNumber = restaurant.display_phone,
                latitude = restaurant.coordinates?.latitude,
                longitude = restaurant.coordinates?.longitude
            )

            fragmentId.postValue(id)
        }


    }

}