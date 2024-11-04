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

class CreateItineraryActivity : AppCompatActivity() {

    private lateinit var nameInput: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_itinerary)

        nameInput = findViewById(R.id.editTextItineraryName)
        findViewById<Button>(R.id.buttonCreate).setOnClickListener {
            createItinerary()
        }
    }

    private fun createItinerary() {
        val name = nameInput.text.toString()
        val itinerario = Itinerario(id = "", nome = name)

        RetrofitClient.apiService.createItinerario(itinerario).enqueue(object : Callback<Itinerario> {
            override fun onResponse(call: Call<Itinerario>, response: Response<Itinerario>) {
                if (response.isSuccessful) {
                    Toast.makeText(this@CreateItineraryActivity, "Itinerário criado com sucesso!", Toast.LENGTH_SHORT).show()
                    finish()
                }
            }

            override fun onFailure(call: Call<Itinerario>, t: Throwable) {
                Toast.makeText(this@CreateItineraryActivity, "Falha ao criar itinerário", Toast.LENGTH_SHORT).show()
            }
        })
    }
}
