package com.example.organizapp

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

/*

Proyecto realizado por: Antonio, Paula y Denisa

Descripción: Clase encargada de recibir datos de entrada del usuario y validarlos mediante Firebase.
    Si los datos son correctos le dejará entrar en la aplicación.
    Si son incorrectos dará un error.
    Una vez haya iniciado sesión, al volver a pasar por esta clase el usuario logueará automáticamente hasta que decida cerrar sesión.

*/

class Login : AppCompatActivity() {

    private lateinit var loginEmail: EditText
    private lateinit var loginPass: EditText
    private lateinit var loginBoton: Button
    private lateinit var loginRegistro: Button

    private lateinit var auth: FirebaseAuth

    companion object{
        var keyUser = ""
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        auth = Firebase.auth

        loginEmail = findViewById(R.id.loginEmail)
        loginPass = findViewById(R.id.loginPass)
        loginBoton = findViewById(R.id.loginBoton)
        loginRegistro = findViewById(R.id.loginRegistro)

        buscarDatos()

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

    private fun buscarDatos() {
        val sharedPreferences = getSharedPreferences("sharedPrefs", Context.MODE_PRIVATE)
        val emailGuardado = sharedPreferences.getString("email", "")
        val passGuardada = sharedPreferences.getString("pass", "")

        if(emailGuardado!="" && passGuardada!=""){

            if (emailGuardado != null) { //sino no deja¿?¿?
                if (passGuardada != null) { //sino no deja¿?¿?
                    login(emailGuardado, passGuardada)
                    Toast.makeText(applicationContext, "Bienvenido " + emailGuardado, Toast.LENGTH_SHORT).show()
                }
            }
        }

    }

    private fun login(email: String, pass: String) {
        //Acceder
        auth.signInWithEmailAndPassword(email, pass)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    startActivity(Intent(this, Pantalla1::class.java))

                    //guarda los datos
                    //https://www.youtube.com/watch?v=S5uLAGnBvUY
                    val sharedPreferences = getSharedPreferences("sharedPrefs", Context.MODE_PRIVATE)
                    val editor = sharedPreferences.edit()
                    editor.apply{
                        putString("email", email)
                        putString("pass", pass) //encriptar?
                    }.apply()

                    keyUser = email //la clave para luego buscar usuario
                    //se guarda en el companion object para que se comparta entre clases

                    finish()
                } else {
                    Toast.makeText(applicationContext, "Usuario o contraseña incorrectos", Toast.LENGTH_LONG).show()

                }
            }
    }
    private fun sinVacios(email: String, pass: String): Boolean {
        return email.isNotEmpty() && pass.isNotEmpty()
    }
}


