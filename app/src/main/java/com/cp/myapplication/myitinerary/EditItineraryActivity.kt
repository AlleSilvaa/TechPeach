package com.cp.myapplication.myitinerary

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.cp.myapplication.R
import com.cp.myapplication.network.Itinerario
import com.cp.myapplication.network.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class EditItineraryActivity : AppCompatActivity() {

    private lateinit var nameInput: EditText
    private lateinit var itineraryId: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_itinerary)

        nameInput = findViewById(R.id.editTextItineraryName)
        itineraryId = intent.getStringExtra("ITINERARIO_ID") ?: ""

        // Carregar os dados do itinerário existente
        loadItinerary()

        findViewById<Button>(R.id.buttonUpdate).setOnClickListener {
            updateItinerary()
        }
    }

    private fun loadItinerary() {
        // Aqui você poderia fazer uma chamada para carregar os detalhes do itinerário
    }

    private fun updateItinerary() {
        val name = nameInput.text.toString()
        val itinerario = Itinerario(id = itineraryId, nome = name)

        RetrofitClient.apiService.updateItinerario(itineraryId, itinerario).enqueue(object : Callback<Itinerario> {
            override fun onResponse(call: Call<Itinerario>, response: Response<Itinerario>) {
                if (response.isSuccessful) {
                    Toast.makeText(this@EditItineraryActivity, "Itinerário atualizado com sucesso!", Toast.LENGTH_SHORT).show()
                    finish()
                }
            }

            override fun onFailure(call: Call<Itinerario>, t: Throwable) {
                Toast.makeText(this@EditItineraryActivity, "Falha ao atualizar itinerário", Toast.LENGTH_SHORT).show()
            }
        })
    }
}
