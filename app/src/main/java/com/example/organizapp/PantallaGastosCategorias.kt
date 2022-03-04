
package com.example.organizapp

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import lecho.lib.hellocharts.model.PieChartData
import lecho.lib.hellocharts.model.SliceValue
import lecho.lib.hellocharts.view.PieChartView
import java.time.LocalDate
import java.time.Month

/*
ESTA CLASE NO SE USA
 */

private lateinit var chart: PieChartView

class PantallaGastosCategorias : AppCompatActivity() {

    private lateinit var fecha: EditText
    var pieChartView: PieChartView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pantalla_gastos_categorias)


        fecha = findViewById(R.id.inputFecha)
        var f = LocalDate.now()
        fecha.setText("${mesSpanish(f.monthValue)} de ${f.year}")
        fecha.setOnClickListener { showDatePickerDialog() }

        lateinit var btnSalir: Button
        lateinit var btnMas: Button


        btnSalir = findViewById(R.id.BotonSalirC)
        btnMas = findViewById(R.id.BotonMasC)

        var pieChartView = findViewById<PieChartView>(R.id.chart)
        pieChartView = findViewById(R.id.chart)

        val pieData: MutableList<SliceValue> = ArrayList<SliceValue>()

        val s: SliceValue = SliceValue(25f, Color.GRAY).setLabel("Q2:$4")

        pieData.add(SliceValue(10f, Color.RED).setLabel("Facturas: 100€"))
        pieData.add(SliceValue(10f, Color.GREEN).setLabel("Regalos: 100"))
        pieData.add(SliceValue(10f, Color.BLUE).setLabel("Vacaciones: 100€"))
        pieData.add(SliceValue(10f, Color.DKGRAY).setLabel("Salud: 100€"))
        pieData.add(SliceValue(10f, Color.CYAN).setLabel("Compras: 100€"))
        pieData.add(SliceValue(10f, Color.MAGENTA).setLabel("Comida: 100€"))
        pieData.add(SliceValue(10f, Color.YELLOW).setLabel("Vehiculos: 100€"))
        pieData.add(SliceValue(10f, Color.LTGRAY).setLabel("Gasolina: 100€"))
        pieData.add(SliceValue(10f, Color.WHITE).setLabel("Casa: 100€"))

        val pieChartData = PieChartData(pieData)

        pieChartData.setHasLabels(true).valueLabelTextSize = 10
        pieChartData.setHasCenterCircle(true).setCenterText1("Gastos")
            .setCenterText1FontSize(15).centerText1Color = Color.parseColor("#0097A7")
        pieChartView.pieChartData = pieChartData

        btnSalir.setOnClickListener {
            val intent = Intent(this, Pantalla1::class.java)
            startActivity(intent) }

        btnMas.setOnClickListener {
                val intent = Intent(this, AddMovimientoCategorias::class.java)
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



