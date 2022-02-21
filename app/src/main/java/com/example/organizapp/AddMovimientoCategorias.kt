package com.example.organizapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import java.time.LocalDate

class AddMovimientoCategorias : AppCompatActivity() {

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
        setContentView(R.layout.activity_add_movimiento_categorias)
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
                Log.i(AddMovimientoActivity.TAG, "Tipo: $seleccion")
            }

        }

        //Listener de la fecha
        fecha.setOnClickListener { showDatePickerDialog() }

        aceptar.setOnClickListener{
            guardarDatosFB()
        }

        //lo pongo aqui para probar, hay que quitarlo de este boton
        cancelar.setOnClickListener {
            recogerDatosFB()
        }

    }

    private fun recogerDatosFB() {
        //https://firebase.google.com/docs/firestore/query-data/queries?hl=es-419#collection-group-query
        val db = FirebaseFirestore.getInstance()

        val gastos = db.collection("Usuarios").document("key") .collection(seleccion.toString())

        gastos.get().addOnSuccessListener { documents ->
            for (document in documents) {
                Log.d(AddMovimientoActivity.TAG, "${document.id} => ${document.data}")
            }
        }

    }

    private fun guardarDatosFB(){
        val db = FirebaseFirestore.getInstance()

        //los busca todos y los cuenta para ver cuantos hay
        var cuantos = 0
        val todos = db.collection("Usuarios").document("key") .collection(seleccion.toString())
        todos.get().addOnSuccessListener { documents -> //esto es un hilo ¿?¿?¿?
            for (document in documents) {
                cuantos += 1
                Log.d(AddMovimientoActivity.TAG, "pasa " + cuantos.toString())

                //lo introduce
                Log.d(AddMovimientoActivity.TAG, "despues " + cuantos.toString())
                //https://firebase.google.com/docs/firestore/data-model?hl=es-419
                db.collection("Usuarios").document("key").collection(seleccion.toString()).document(seleccion.toString() + cuantos.toString()).set(
                    hashMapOf(
                        "Nombre" to nombre.text.toString(),
                        "Importe" to importe.text.toString(),
                        "Fecha" to fecha.text.toString()
                    )
                )


            }
        }.addOnFailureListener {
            Log.d(AddMovimientoActivity.TAG, "mal")
        }


        /*
        db.collection("usu").document("key").set(
            hashMapOf(
                "correo" to "correo1",
                "pass" to "pass1"
            )
        )
        db.collection("usu").document("key").collection("gastos").document("gasto1").set(
            hashMapOf(
                "nombre" to "nombre1",
                "cantidad" to "50"
            )
        )
        db.collection("usu").document("key").collection("ingresos").document("ingreso1").set(
            hashMapOf(
                "nombre" to "nombre2",
                "cantidad" to "60"
            )
        )
        db.collection("usu").document("key").collection("ingresos").document("ingreso2").set(
            hashMapOf(
                "nombre" to "nombre3",
                "cantidad" to "10"
            )
        )
        */
        /*
        db.collection("a").document("b").set(
            hashMapOf(
                "c" to "c",
                "d" to "d"
            )
        )
        */

        //db.collection("a").document("b").set("asadsa" to "ada")

        /*
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
       */

        /*
        db.collection("Usuarios").document("usuario1").set(
          db.document(seleccion.toString()).set(
                db.document(nombre.text.toString()).set(
                    hashMapOf(
                        "Importe" to importe.text.toString(),
                        "Fecha" to fecha.text.toString()
                    )
                )
            )
                    /*
            hashMapOf("Tipo" to seleccion.toString(),
                "Nombre" to nombre.text.toString(),
                "Importe" to importe.text.toString(),
                //"Importe" to Integer.parse(importe.toString()),
                "Fecha" to fecha.text.toString()
            )

                     */

        ).addOnSuccessListener { Toast.makeText(this, "Movimiento realizado correctamente",
            Toast.LENGTH_SHORT
        ).show() }.addOnFailureListener { Toast.makeText(this, "Movimiento incorrecto",
            Toast.LENGTH_SHORT
        ).show()}
         */


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
            R.array.tiposCategorias, //Lista de opciones
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

        Log.i(AddMovimientoActivity.TAG, "Fecha: $fechaSeleccionada")
    }

}