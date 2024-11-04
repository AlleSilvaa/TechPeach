package com.cp.myapplication.myitinerary

import android.content.Context
import android.content.SharedPreferences

class PreferencesManager(context: Context) {
    private val prefs: SharedPreferences = context.getSharedPreferences("app_prefs", Context.MODE_PRIVATE)

    fun saveItineraryId(id: String) {
        prefs.edit().putString("itinerary_id", id).apply()
    }

    fun getItineraryId(): String? {
        return prefs.getString("itinerary_id", null)
    }
}
