package com.cp.myapplication.myitinerary

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.cp.myapplication.R
import com.cp.myapplication.network.Itinerario
import com.cp.myapplication.network.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MyItineraryActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var itineraryAdapter: ItineraryAdapter
    private val itineraries = mutableListOf<Itinerario>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_itinerary)

        recyclerView = findViewById(R.id.recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(this)

        itineraryAdapter = ItineraryAdapter(itineraries) { itinerary ->
            val intent = Intent(this, EditItineraryActivity::class.java).apply {
                putExtra("ITINERARIO_ID", itinerary.id)
            }
            startActivity(intent)
        }

        recyclerView.adapter = itineraryAdapter

        loadItineraries()

        findViewById<Button>(R.id.buttonAdd).setOnClickListener {
            val intent = Intent(this, CreateItineraryActivity::class.java)
            startActivity(intent)
        }
    }

    private fun loadItineraries() {
        val call: Call<List<Itinerario>> = RetrofitClient.apiService.getItinerarios()
        call.enqueue(object : Callback<List<Itinerario>> {
            override fun onResponse(call: Call<List<Itinerario>>, response: Response<List<Itinerario>>) {
                if (response.isSuccessful) {
                    val itinerariesFromApi = response.body() ?: emptyList()
                    itineraries.clear()
                    itineraries.addAll(itinerariesFromApi)
                    itineraryAdapter.notifyDataSetChanged()
                }
            }

            override fun onFailure(call: Call<List<Itinerario>>, t: Throwable) {
                // Lidar com falhas na chamada
            }
        })
    }
}
