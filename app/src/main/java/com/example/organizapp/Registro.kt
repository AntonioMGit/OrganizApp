package com.example.organizapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import android.widget.Toast.LENGTH_SHORT
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.google.firebase.firestore.FirebaseFirestore

/*

Proyecto realizado por: Antonio, Paula y Denisa

Descripción: Clase que permite, mediante un formulario, registrar usuarios en la aplicación.
    Estos datos irán a Firebase que se encargará de comprobarlos.
    Si es correcto y el usuario cumple con las condiciones se registrará.
    Si no es correcto o el usuario ya existe dará un aviso.

*/

class Registro : AppCompatActivity() {

    private lateinit var RegistroNombre: EditText
    private lateinit var RegistroApellido: EditText
    private lateinit var registroEmail: EditText
    private lateinit var registroPass: EditText
    private lateinit var registroPass2: EditText
    private lateinit var registroBoton: Button
    private lateinit var YaTengoCuenta: Button

    private lateinit var auth: FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registro)

        auth = Firebase.auth

        RegistroNombre = findViewById(R.id.RegistroNombre)
        RegistroApellido = findViewById(R.id.RegistroApellido)
        registroEmail = findViewById(R.id.registroEmail)
        registroPass = findViewById(R.id.registroPass)
        registroPass2 = findViewById(R.id.registroPass2)
        registroBoton = findViewById(R.id.registroBoton)
        YaTengoCuenta = findViewById(R.id.YaTengoCuenta)

        registroBoton.setOnClickListener{

            val nombre= RegistroNombre.text.toString()
            val apellido= RegistroApellido.text.toString()
            val email = registroEmail.text.toString()
            val pass = registroPass.text.toString()
            val pass2 = registroPass2.text.toString()

            if (pass == pass2 && sinVacios(email,pass,pass2)){
                registro (email,pass)
                val db = FirebaseFirestore.getInstance()

                db.collection("Usuario").document(email.toString()).set(
                    hashMapOf("Nombre" to nombre.toString(), "Apellidos" to apellido.toString())
                ).addOnSuccessListener { Toast.makeText(this, "Registro realizado correctamente", LENGTH_SHORT).show() }.addOnFailureListener { Toast.makeText(this, "Registro incorrecto", LENGTH_SHORT).show()}

            }

        }
        YaTengoCuenta.setOnClickListener{
            startActivity(Intent(this, Login::class.java))
            finish()
        }
    }


    private fun registro(email: String, pass: String) {
        //tiene metodos propios
        //Creamos el email y el password
        //Cuando se complete la tarea, en este contexto,
        //comprobamos si el task es satisfactoria y el usuario se ha registrado correctamente
        // sino mostramos un mensaje de que hay un error

        auth.createUserWithEmailAndPassword(email,pass)
            .addOnCompleteListener(this){ task ->
                if(task.isSuccessful){
                    var intent = Intent (this, MainActivity::class.java)
                    startActivity(intent)
                    finish()
                }else{
                    Toast.makeText(applicationContext,"Error", Toast.LENGTH_LONG).show()

                }
            }
    }

    private fun sinVacios(email: String, pass: String, pass2: String): Boolean {
        return email.isNotEmpty() && pass.isNotEmpty() && pass2.isNotEmpty()

    }
}