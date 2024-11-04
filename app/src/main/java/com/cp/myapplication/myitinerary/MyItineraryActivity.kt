package com.cp.planejeja.myitinerary

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.cp.planejeja.R
import com.cp.planejeja.network.RetrofitClient
import com.cp.planejeja.model.Itinerario
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MyItineraryActivity : AppCompatActivity() {

    private lateinit var itineraryAdapter: ItineraryAdapter
    private lateinit var itineraryList: List<Itinerario>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_itinerary)

        // Configura RecyclerView
        val recyclerView: RecyclerView = findViewById(R.id.recycler_view)
        itineraryAdapter = ItineraryAdapter(itineraryList) { itinerary ->
            openEditItinerary(itinerary)
        }
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = itineraryAdapter

        // Botão para adicionar novo itinerário
        findViewById<Button>(R.id.buttonAdd).setOnClickListener {
            openCreateItinerary()
        }

        loadItineraries()
    }

    private fun loadItineraries() {
        CoroutineScope(Dispatchers.IO).launch {
            val response = RetrofitClient.apiService.getItinerarios()
            withContext(Dispatchers.Main) {
                itineraryList = response
                itineraryAdapter.updateList(itineraryList) // Atualiza a lista no adapter
            }
        }
    }

    private fun openCreateItinerary() {
        val intent = Intent(this, CreateItineraryActivity::class.java)
        startActivity(intent)
    }

    private fun openEditItinerary(itinerary: Itinerario) {
        val intent = Intent(this, EditItineraryActivity::class.java)
        intent.putExtra("itinerary_id", itinerary.id)
        startActivity(intent)
    }
}
