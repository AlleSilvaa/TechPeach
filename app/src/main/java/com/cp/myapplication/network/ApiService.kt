package com.cp.myapplication.network

import retrofit2.Call
import retrofit2.http.GET

interface ApiService {
    @GET("itinerarios")
    fun getItinerarios(): Call<List<Itinerario>>
}
