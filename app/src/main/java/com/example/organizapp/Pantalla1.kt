package com.example.organizapp

import android.content.Context
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.firestore.FirebaseFirestore
import lecho.lib.hellocharts.model.PieChartData
import lecho.lib.hellocharts.model.SliceValue
import lecho.lib.hellocharts.view.PieChartView
import java.time.LocalDate
import java.time.Month

/*

Proyecto realizado por: Antonio, Paula y Denisa

Descripción: Clase que muetra al usuario un gráfico circular de los gastos e ingresos de un mes concreto; por defecto el actual.
    El usuario podrá cambiar la vista del mes para elegir el que desee ver.
    Los datos se recogen mediante Firebase y se muestras con PieChartView.
    También dispone de varios botones para desplazarse a otras pantallas de la aplicación.

*/

private lateinit var chart: PieChartView

class Pantalla1 : AppCompatActivity() {

    private lateinit var fecha: EditText
    var pieChartView: PieChartView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pantalla1)

        fecha = findViewById(R.id.inputFecha)
        var f = LocalDate.now()
        fecha.setText("${mesSpanish(f.monthValue)} de ${f.year}")
        fecha.setOnClickListener { showDatePickerDialog() }

        lateinit var btnMas: Button
        lateinit var btnGrafica: Button
        lateinit var btnLista: Button
        lateinit var btnSesion: Button


        btnMas = findViewById(R.id.BotonMas)
        btnGrafica = findViewById(R.id.BotonGrafica)
        btnLista = findViewById(R.id.BotonListado)
        btnSesion = findViewById(R.id.cerrarSesion)


        ponerDatosEnGrafica(f.monthValue)

        btnSesion.setOnClickListener{
            val sharedPreferences = getSharedPreferences("sharedPrefs", Context.MODE_PRIVATE)
            val editor = sharedPreferences.edit()
            editor.apply{
                putString("email", "")
                putString("pass", "")
            }.apply()

            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)

            Toast.makeText(applicationContext,"Se ha cerrado sesión", Toast.LENGTH_LONG).show()
        }


        btnLista.setOnClickListener {
            val intent = Intent(this, ListadoMovimientos::class.java)
            startActivity(intent) }


        btnMas.setOnClickListener {
            val intent = Intent(this, AddMovimientoActivity::class.java)
            startActivity(intent) }

        btnGrafica.setOnClickListener {
            val intent = Intent(this, GraficosPorMeses::class.java)
            startActivity(intent) }

    }


    //Inicializar el dialogo creado en onCreateDialog de la clase DatePickerDialog
    private fun showDatePickerDialog() {
        //Crear un objeto de la clase DatePickerDialog
        val datePicker = DatePickerFragment { day, month, year -> onDateSelected(day, month, year) }
        datePicker.show(supportFragmentManager, "datePicker")
    }

    //Funcion que se le pasa a DatePickerDialog al crearlo. Cuando ya se ha seleccionado una fecha, se llama a este metodo
    private fun onDateSelected(day:Int, month:Int, year:Int){
        fecha.setText("${(mesSpanish(month+1))} de $year")
        //Toast.makeText(applicationContext, "Mes: " + month, Toast.LENGTH_LONG).show()
        ponerDatosEnGrafica(month+1)
    }

    private fun ponerDatosEnGrafica(month:Int){

        val db = FirebaseFirestore.getInstance()

        val gastos = db.collection("Usuarios").document(Login.keyUser) .collection("Gasto")
        val ingresos = db.collection("Usuarios").document(Login.keyUser) .collection("Ahorro")
        var fechaSeparada: List<String>

        val pieData: MutableList<SliceValue> = ArrayList<SliceValue>()

        var pieChartView = findViewById<PieChartView>(R.id.chart)
        pieChartView = findViewById(R.id.chart)

        val s: SliceValue = SliceValue(25f, Color.GRAY).setLabel("Q2:$4")

        gastos.get().addOnSuccessListener { documents ->
            for (document in documents) {
                //Log.d("noseque", "${document.id} => ${document.data}")

                fechaSeparada = document.data.get("Fecha").toString().split("/")

                var numMes = fechaSeparada.get(1)
                //Toast.makeText(applicationContext, "Mes: " + numMes, Toast.LENGTH_LONG).show()
                if(numMes.equals(month.toString())) {
                    //Log.d("noseque", "pasa")
                    pieData.add(SliceValue(document.get("Importe").toString().toFloat(), Color.RED).setLabel(document.data.get("Nombre").toString() + ": " + document.get("Importe").toString()))

                }

            }
            val pieChartData = PieChartData(pieData)

            pieChartData.setHasLabels(true).valueLabelTextSize= 10
            pieChartData.setHasCenterCircle(true).setCenterText1("Gastos/Ingresos").setCenterText1FontSize(15).centerText1Color= Color.parseColor("#0097A7")
            pieChartView.pieChartData = pieChartData
        }

        ingresos.get().addOnSuccessListener { documents ->
            for (document in documents) {
                //Log.d("noseque", "${document.id} => ${document.data}")

                fechaSeparada = document.data.get("Fecha").toString().split("/")

                var numMes = fechaSeparada.get(1)
                //Toast.makeText(applicationContext, "Mes: " + numMes, Toast.LENGTH_LONG).show()
                if(numMes.equals(month.toString())) {
                    pieData.add(SliceValue(document.get("Importe").toString().toFloat(), Color.GREEN).setLabel(document.data.get("Nombre").toString() + ": " + document.get("Importe").toString()))
                }

            }
            val pieChartData = PieChartData(pieData)

            pieChartData.setHasLabels(true).valueLabelTextSize= 10
            pieChartData.setHasCenterCircle(true).setCenterText1("Gastos/Ingresos").setCenterText1FontSize(15).centerText1Color= Color.parseColor("#0097A7")
            pieChartView.pieChartData = pieChartData
        }


        //pieData.add(SliceValue(100f, Color.GREEN).setLabel(document.data.get("Nombre").toString() + ": " + document.get("Importe").toString()))
    }

    private fun mesSpanish(numero: Int): String{
        var mes: String = ""
        when (numero) {
            1 -> mes = "Enero"
            2 -> mes = "Febrero"
            3 -> mes = "Marzo"
            4 -> mes = "Abril"
            5 -> mes = "Mayo"
            6 -> mes = "Junio"
            7 -> mes = "Julio"
            8 -> mes = "Agosto"
            9 -> mes = "Septiembre"
            10 -> mes = "Octubre"
            11 -> mes = "Noviembre"
            12 -> mes = "Diciembre"
        }
        return mes
    }
}




