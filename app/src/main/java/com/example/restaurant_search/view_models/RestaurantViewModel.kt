package com.example.restaurant_search.view_models

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.example.restaurant_search.default
import com.example.restaurant_search.models.Restaurant

class RestaurantViewModel(private val restaurant: Restaurant) : ViewModel() {

    var addressLabel = MutableLiveData<String?>().default(restaurant.address)
    var priceLabel = MutableLiveData<String?>().default(restaurant.priceRange)
    var phoneNumberLabel = MutableLiveData<String?>().default(restaurant.phoneNumber)

}