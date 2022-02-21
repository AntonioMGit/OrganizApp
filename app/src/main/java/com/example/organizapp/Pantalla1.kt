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

class Pantalla1 : AppCompatActivity() {

    var pieChartView: PieChartView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pantalla1)

        lateinit var btnMas: Button
        lateinit var btnGraficoCategorias: Button
        //lateinit var btnLista: Button


        btnMas = findViewById(R.id.BotonMas)
        btnGraficoCategorias = findViewById(R.id.BotonGrafica)
        //btnLista = findViewById(R.id.BotonListado)

        var pieChartView = findViewById<PieChartView>(R.id.chart)
        pieChartView = findViewById(R.id.chart)

        val pieData: MutableList<SliceValue> = ArrayList<SliceValue>()

        val s: SliceValue = SliceValue(25f, Color.GRAY).setLabel("Q2:$4")

        pieData.add(SliceValue(54f, Color.RED).setLabel("Gastos: 600€"))
        pieData.add(SliceValue(100f, Color.GREEN).setLabel("Ingresos: 1100€"))
        val pieChartData = PieChartData(pieData)

        pieChartData.setHasLabels(true).valueLabelTextSize= 10
        pieChartData.setHasCenterCircle(true).setCenterText1("Gastos/Ingresos")
            .setCenterText1FontSize(15).centerText1Color= Color.parseColor("#0097A7")
        pieChartView.pieChartData = pieChartData

        btnMas.setOnClickListener {
            val intent = Intent(this, AddMovimientoActivity::class.java)
            startActivity(intent) }

        btnGraficoCategorias.setOnClickListener {
            val intent = Intent(this, PantallaGastosCategorias::class.java)
            startActivity(intent) }
        /*
        btnLista.setOnClickListener {
            val intent = Intent(this, PantallaGastosCategorias::class.java)
            startActivity(intent) } */
    }
}




