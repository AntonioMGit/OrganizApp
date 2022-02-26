package com.example.organizapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.Spinner
import android.util.Log
import android.view.View
import android.widget.*
import com.example.organizapp.Login.Companion.keyUser
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import java.time.LocalDate


class AddMovimientoActivity : AppCompatActivity() {
    private lateinit var spinner: Spinner
    private lateinit var nombre : EditText
    private lateinit var importe : EditText
    private lateinit var fecha: EditText
    private lateinit var cancelar: Button
    private lateinit var aceptar: Button
    private var seleccion: String = ""
    private lateinit var fechaSeleccionada: LocalDate

    private lateinit var auth: FirebaseAuth

    companion object{
        val TAG = "Variables"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_movimiento)

        auth = Firebase.auth

        inicializarVariables()

        //Listener del spinner
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {
                seleccion= "Seleccionar"
            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                if (parent != null) {
                    seleccion = parent.getItemAtPosition(position).toString()
                }
                Log.i(TAG, "Tipo: $seleccion")
            }

        }

        //Listener de la fecha
        fecha.setOnClickListener { showDatePickerDialog() }

        aceptar.setOnClickListener{
            guardarDatosFB()
            startActivity(Intent(this, Pantalla1::class.java))
        }

        //lo pongo aqui para probar, hay que quitarlo de este boton
        cancelar.setOnClickListener {
            //recogerDatosFB()
            startActivity(Intent(this, Pantalla1::class.java))
        }

    }

    private fun recogerDatosFB() {
        //https://firebase.google.com/docs/firestore/query-data/queries?hl=es-419#collection-group-query
        val db = FirebaseFirestore.getInstance()

        val gastos = db.collection("Usuarios").document(keyUser) .collection(seleccion.toString())

        gastos.get().addOnSuccessListener { documents ->
            for (document in documents) {
                Log.d(TAG, "${document.id} => ${document.data}")
            }
        }

    }

    private fun guardarDatosFB(){
        val db = FirebaseFirestore.getInstance()

        //hay que insertar uno por defecto siempre?
        db.collection("Usuarios").document(keyUser).collection(seleccion.toString()).document(seleccion.toString() + "por defecto").set(
            hashMapOf(
                "Nombre" to "0",
                "Importe" to "0",
                "Fecha" to "0/0/0"
            )
        )

        val todos = db.collection("Usuarios").document(keyUser).collection(seleccion.toString())

        todos.get().addOnSuccessListener { documents -> //esto es un hilo ¿?¿?¿?
            documents.size()
            //lo introduce
            //https://firebase.google.com/docs/firestore/data-model?hl=es-419
            db.collection("Usuarios").document(keyUser).collection(seleccion.toString()).document(seleccion.toString() + documents.size().toString()).set(
                hashMapOf(
                    "Nombre" to nombre.text.toString(),
                    "Importe" to importe.text.toString(),
                    "Fecha" to fecha.text.toString()
                )
            )
            }.addOnFailureListener {
            Log.d(TAG, "mal")
        }

    }

    private fun inicializarVariables() {
        spinner = findViewById(R.id.spinnerTipo)
        nombre = findViewById(R.id.inputNombre)
        importe = findViewById(R.id.inputImporte)
        fecha = findViewById(R.id.inputFecha)
        cancelar = findViewById(R.id.btnCancelar)
        aceptar = findViewById(R.id.btnAceptar)

        configurarSpinner()
    }

    private fun configurarSpinner() {
        ArrayAdapter.createFromResource(
            this,
            R.array.tipos, //Lista de opciones
            android.R.layout.simple_spinner_item //Estilo
        ).also { adapter ->
            //Estilo de la lista de opciones
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            //Adapter
            spinner.adapter = adapter
        }
    }

    //Inicializar el dialogo creado en onCreateDialog de la clase DatePickerDialog
    private fun showDatePickerDialog() {
        //Crear un objeto de la clase DatePickerDialog
        val datePicker = DatePickerFragment { day, month, year -> onDateSelected(day, month, year) }
        datePicker.show(supportFragmentManager, "datePicker")
    }

    //Funcion que se le pasa a DatePickerDialog al crearlo. Cuando ya se ha seleccionado una fecha, se llama a este metodo
    private fun onDateSelected(day:Int, month:Int, year:Int){
        fecha.setText("$day/"+ (month+1) + "/$year")
        fechaSeleccionada = LocalDate.of(year, (month+1), day)

        //Log.i(TAG, "Fecha: $fechaSeleccionada")
    }
}