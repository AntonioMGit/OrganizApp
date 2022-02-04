package com.example.organizapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class Login : AppCompatActivity() {

    private lateinit var loginEmail: EditText
    private lateinit var loginPass: EditText
    private lateinit var loginBoton: Button
    private lateinit var loginRegistro: Button

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        auth = Firebase.auth

        loginEmail = findViewById(R.id.loginEmail)
        loginPass = findViewById(R.id.loginPass)
        loginBoton = findViewById(R.id.loginBoton)
        loginRegistro = findViewById(R.id.loginRegistro)

        loginBoton.setOnClickListener {
            val email = loginEmail.text.toString()
            val pass = loginPass.text.toString()

            if (sinVacios(email, pass)) {
                login(email,pass)

            }

        }

        loginRegistro.setOnClickListener{
            val intent = Intent(this, Registro::class.java)
            startActivity(intent)
        }

    }
    private fun login(email: String, pass: String) {
        //Acceder
        auth.signInWithEmailAndPassword(email, pass)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    startActivity(Intent(this, MainActivity::class.java))
                    finish()
                } else {
                    Toast.makeText(applicationContext, "Error", Toast.LENGTH_LONG).show()

                }
            }
    }
    private fun sinVacios(email: String, pass: String): Boolean {
        return email.isNotEmpty() && pass.isNotEmpty()
    }
}


