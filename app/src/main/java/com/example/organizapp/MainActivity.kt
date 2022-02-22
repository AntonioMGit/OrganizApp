package com.example.organizapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button


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