
package com.example.organizapp

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import lecho.lib.hellocharts.model.PieChartData
import lecho.lib.hellocharts.model.SliceValue
import lecho.lib.hellocharts.view.PieChartView

private lateinit var chart: PieChartView

class PantallaGastosCategorias : AppCompatActivity() {

    var pieChartView: PieChartView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pantalla_gastos_categorias)

        lateinit var btnSalir: Button
        lateinit var btnMas: Button
        // lateinit var btnLista: Button


        btnSalir = findViewById(R.id.BotonSalirC)
        btnMas = findViewById(R.id.BotonMasC)
        //btnLista = findViewById(R.id.BotonListaC)

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

        /*
        btnLista.setOnClickListener {
            val intent = Intent(this, Pantalla1::class.java)
            startActivity(intent)  */


    }
}



