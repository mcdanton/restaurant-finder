package com.example.restaurant_search.view_models

import android.location.Location
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.SearchYelpResQuery
import com.example.restaurant_search.R
import com.example.restaurant_search.models.Restaurant

class NavigationViewModel : ViewModel() {

    val fragmentId = MutableLiveData<Int>((R.layout.fragment_restaurant_list))
    var selectedRestaurant: Restaurant? = null
    var userLocation: Location? = null


    fun updateFragment(id: Int, restaurant: SearchYelpResQuery.Business?) {

        if (restaurant == null) {
            selectedRestaurant = null
            fragmentId.postValue(id)
        } else {
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