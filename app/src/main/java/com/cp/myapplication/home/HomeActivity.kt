package com.cp.myapplication.home

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.cp.myapplication.R
import com.cp.myapplication.login.LoginActivity
import com.cp.myapplication.myitinerary.MyItineraryActivity
import com.cp.myapplication.additinerario.AddItinerarioActivity

class HomeActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        auth = FirebaseAuth.getInstance()

        val logoutButton: Button = findViewById(R.id.button_logout)
        val profileImage: ImageView = findViewById(R.id.image_profile)
        val meusItinerariosButton: Button = findViewById(R.id.button_meus_itinerarios)
        val criarItinerariosButton: Button = findViewById(R.id.button_criar_itinerarios)

        logoutButton.setOnClickListener {
            auth.signOut()
            // Redirecionar para a tela de login após o logout
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }

        profileImage.setOnClickListener {
            // Adicione a lógica para a imagem do perfil, se necessário
        }

        meusItinerariosButton.setOnClickListener {
            val intent = Intent(this, MyItineraryActivity::class.java)
            startActivity(intent)
        }

        criarItinerariosButton.setOnClickListener {
            val intent = Intent(this, AddItinerarioActivity::class.java)
            startActivity(intent)
        }
    }
}
