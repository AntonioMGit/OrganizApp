package com.example.organizapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.Spinner
import android.util.Log
import android.view.View
import android.widget.*
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
        }

    }

    private fun guardarDatosFB(){
        val db = FirebaseFirestore.getInstance()

        db.collection("Movimientos").document(nombre.text.toString()).set(
            hashMapOf("Tipo" to seleccion.toString(),
                "Nombre" to nombre.text.toString(),
                "Importe" to importe.text.toString(),
                //"Importe" to Integer.parse(importe.toString()),
                "Fecha" to fecha.text.toString()
            )
        ).addOnSuccessListener { Toast.makeText(this, "Movimiento realizado correctamente",
            Toast.LENGTH_SHORT
        ).show() }.addOnFailureListener { Toast.makeText(this, "Movimiento incorrecto",
            Toast.LENGTH_SHORT
        ).show()}

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

        Log.i(TAG, "Fecha: $fechaSeleccionada")
    }
}