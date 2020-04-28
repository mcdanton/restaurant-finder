package com.example.restaurant_search.view_models

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.restaurant_search.R
import com.example.restaurant_search.default

class NavigationViewModel : ViewModel() {

    val fragmentId = MutableLiveData<Int>().default(R.layout.fragment_restaurant_list)

    fun updateFragmentId(id: Int) {

        fragmentId.postValue(id)


    }
}