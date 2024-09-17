package com.cp.myapplication.login

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.cp.myapplication.R
import com.cp.myapplication.home.HomeActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthException

class LoginActivity : AppCompatActivity() {

    private lateinit var emailEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var loginButton: Button
    private lateinit var registerButton: Button

    private val auth: FirebaseAuth by lazy { FirebaseAuth.getInstance() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        emailEditText = findViewById(R.id.email_edit_text)
        passwordEditText = findViewById(R.id.password_edit_text)
        loginButton = findViewById(R.id.login_button)
        registerButton = findViewById(R.id.register_button)

        loginButton.setOnClickListener {
            val email = emailEditText.text.toString().trim()
            val password = passwordEditText.text.toString().trim()
            if (email.isNotEmpty() && password.isNotEmpty()) {
                loginUser(email, password)
            } else {
                Toast.makeText(this, "Preencha todos os campos.", Toast.LENGTH_SHORT).show()
            }
        }

        registerButton.setOnClickListener {
            val email = emailEditText.text.toString().trim()
            val password = passwordEditText.text.toString().trim()
            if (email.isNotEmpty() && password.isNotEmpty()) {
                registerUser(email, password)
            } else {
                Toast.makeText(this, "Preencha todos os campos.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun loginUser(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnSuccessListener {
                // Success
                Toast.makeText(this, "Login bem-sucedido!", Toast.LENGTH_SHORT).show()
                navigateToHome()
            }
            .addOnFailureListener { exception ->
                // Failure
                handleFirebaseAuthError(exception)
            }
    }

    private fun registerUser(email: String, password: String) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnSuccessListener {
                // Success
                Toast.makeText(this, "Cadastro bem-sucedido!", Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener { exception ->
                // Failure
                handleFirebaseAuthError(exception)
            }
    }

    private fun handleFirebaseAuthError(exception: Exception) {
        if (exception is FirebaseAuthException) {
            val errorCode = exception.errorCode
            when (errorCode) {
                "ERROR_INVALID_EMAIL" -> Toast.makeText(this, "Email inválido.", Toast.LENGTH_SHORT).show()
                "ERROR_WRONG_PASSWORD" -> Toast.makeText(this, "Senha incorreta.", Toast.LENGTH_SHORT).show()
                "ERROR_EMAIL_ALREADY_IN_USE" -> Toast.makeText(this, "Email já em uso.", Toast.LENGTH_SHORT).show()
                else -> Toast.makeText(this, "Erro: ${exception.message}", Toast.LENGTH_SHORT).show()
            }
        } else {
            Toast.makeText(this, "Erro: ${exception.message}", Toast.LENGTH_SHORT).show()
        }
    }

    private fun navigateToHome() {
        val intent = Intent(this, HomeActivity::class.java)
        startActivity(intent)
        finish()
    }
}
