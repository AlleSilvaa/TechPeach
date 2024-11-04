package com.cp.myapplication.myitinerary

import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.cp.myapplication.R
import com.cp.myapplication.network.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DeleteItineraryActivity : AppCompatActivity() {

    private lateinit var itineraryId: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_delete_itinerary)

        itineraryId = intent.getStringExtra("ITINERARIO_ID") ?: ""

        findViewById<Button>(R.id.buttonDelete).setOnClickListener {
            deleteItinerary()
        }
    }

    private fun deleteItinerary() {
        RetrofitClient.apiService.deleteItinerario(itineraryId).enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                if (response.isSuccessful) {
                    Toast.makeText(this@DeleteItineraryActivity, "Itinerário excluído com sucesso!", Toast.LENGTH_SHORT).show()
                    finish()
                }
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                Toast.makeText(this@DeleteItineraryActivity, "Falha ao excluir itinerário", Toast.LENGTH_SHORT).show()
            }
        })
    }
}
