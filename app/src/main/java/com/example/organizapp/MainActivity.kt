package com.example.organizapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

/*

Proyecto realizado por: Antonio, Paula y Denisa

Descripción: Clase principal de la aplicación en la que aparece el logo y un botón para ir a iniciar sesión.

*/

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        lateinit var btnAcceso: Button

        btnAcceso = findViewById(R.id.btnAcceso)

        btnAcceso.setOnClickListener {
            // Abrir nueva activity.
            val intent = Intent(this, Login::class.java)
            startActivity(intent)
        }

    }
}