package com.cp.planejeja.network

import retrofit2.http.*
import retrofit2.Response

interface ApiService {
    @GET("itinerarios")
    suspend fun getItinerarios(): List<Itinerario>

    @POST("itinerarios")
    suspend fun createItinerario(@Body itinerario: Itinerario): Response<Unit>

    @PUT("itinerarios/{id}")
    suspend fun updateItinerario(@Path("id") id: String, @Body itinerario: Itinerario): Response<Unit>

    @DELETE("itinerarios/{id}")
    suspend fun deleteItinerario(@Path("id") id: String): Response<Unit>
}
