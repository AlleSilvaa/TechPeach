package com.cp.myapplication.network

import retrofit2.Call
import retrofit2.http.*

interface ApiService {
    @GET("itinerarios")
    fun getItinerarios(): Call<List<Itinerario>>

    @POST("itinerarios")
    fun createItinerario(@Body itinerario: Itinerario): Call<Itinerario>

    @PUT("itinerarios/{id}")
    fun updateItinerario(@Path("id") id: String, @Body itinerario: Itinerario): Call<Itinerario>

    @DELETE("itinerarios/{id}")
    fun deleteItinerario(@Path("id") id: String): Call<Void>
}
