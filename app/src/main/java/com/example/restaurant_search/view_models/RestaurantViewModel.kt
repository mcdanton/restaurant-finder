package com.example.restaurant_search.view_models

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.restaurant_search.models.Restaurant

class RestaurantViewModel(private val restaurant: Restaurant) : ViewModel() {

    var addressLabel = MutableLiveData<String?>(restaurant.address)
    var priceLabel = MutableLiveData<String?>(restaurant.priceRange)
    var phoneNumberLabel = MutableLiveData<String?>(restaurant.phoneNumber)

}