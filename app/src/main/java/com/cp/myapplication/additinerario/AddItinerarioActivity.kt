package com.cp.myapplication.additinerario

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.cp.myapplication.R
import com.cp.myapplication.model.Itinerario
import com.cp.myapplication.network.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AddItinerarioActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_itinerario)

        // Chamada da API
        RetrofitClient.instance.getItinerarios().enqueue(object : Callback<List<Itinerario>> {
            override fun onResponse(call: Call<List<Itinerario>>, response: Response<List<Itinerario>>) {
                if (response.isSuccessful) {
                    val itinerarios = response.body()
                    itinerarios?.let {
                        for (itinerario in it) {
                            Log.d("Itinerario", "Nome: ${itinerario.nome}, Descrição: ${itinerario.descricao}")
                        }
                    }
                }
            }

            override fun onFailure(call: Call<List<Itinerario>>, t: Throwable) {
                Log.e("API Error", "Falha ao carregar itinerários", t)
            }
        })
    }
}
