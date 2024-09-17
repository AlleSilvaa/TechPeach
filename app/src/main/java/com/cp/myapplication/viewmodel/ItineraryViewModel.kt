package com.cp.myapplication.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ItineraryViewModel : ViewModel() {

    private val _itinerary = MutableLiveData<String>()
    val itinerary: LiveData<String> get() = _itinerary

    fun setItinerary(itinerary: String) {
        _itinerary.value = itinerary
    }
}
