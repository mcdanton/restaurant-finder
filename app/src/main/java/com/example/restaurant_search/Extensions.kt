package com.example.restaurant_search

import androidx.lifecycle.MutableLiveData

fun <T : Any?> MutableLiveData<T>.default(initialValue: T) = apply {
    setValue(initialValue)
}
