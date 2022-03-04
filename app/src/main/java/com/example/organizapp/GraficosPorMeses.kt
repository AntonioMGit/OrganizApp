package com.example.organizapp

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.FirebaseFirestore
import lecho.lib.hellocharts.model.*
import lecho.lib.hellocharts.view.LineChartView
import java.util.*

/*

Proyecto realizado por: Antonio, Paula y Denisa

Descripción: Clase encargada de pintar dos gráficas, una de gastos y otra de ingresos de cada mes en un año.
    Recoge los datos de Firebase y los pinta mediante LineChartView.

*/

private lateinit var chart: LineChartView
private lateinit var chart2: LineChartView
class GraficosPorMeses : AppCompatActivity() {

    private var axisData: Array<String> = arrayOf(
        "Jan",
        "Feb",
        "Mar",
        "Apr",
        "May",
        "June",
        "July",
        "Aug",
        "Sept",
        "Oct",
        "Nov",
        "Dec"
    )


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_graficos_por_meses)

        var anio = Calendar.getInstance().get(Calendar.YEAR).toString()

        buscarEnAnio(anio, "Gasto")

        buscarEnAnio(anio, "Ahorro")

    }

    private fun buscarEnAnio(anio: String, tipo:String){

        val db = FirebaseFirestore.getInstance()
        val gastos = db.collection("Usuarios").document(Login.keyUser) .collection(tipo.toString())
        var fechaSeparada: List<String>

        var gastosArray: IntArray = intArrayOf(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0)

        gastos.get().addOnSuccessListener { documents ->
            for (document in documents) {

                fechaSeparada = document.data.get("Fecha").toString().split("/")

                var numAnio= fechaSeparada.get(2)
                var numMes= fechaSeparada.get(1)

                if(numAnio.equals(anio.toString())) {
                    gastosArray[numMes.toInt()-1] += document.data.get("Importe").toString().toInt()
                }
            }

            crearGrafico(gastosArray, tipo)
        }

    }

    private fun crearGrafico(gastosArray: IntArray, tipo:String) {

        val yAxisValues:ArrayList<PointValue> = ArrayList()
        val axisValues:ArrayList<AxisValue> = ArrayList()

        val line = Line(yAxisValues).setColor(Color.parseColor("#9C27B0"))
        for (i in axisData.indices) {
            axisValues.add(i, AxisValue(i.toFloat()).setLabel(axisData[i]))
        }
        for (i in gastosArray.indices) {
            yAxisValues.add(PointValue(i.toFloat(), gastosArray[i].toFloat()))
        }

        val lines:ArrayList<Line> = ArrayList()
        lines.add(line)

        val data = LineChartData()
        data.lines = lines

        val axis = Axis()
        axis.values = axisValues
        axis.textSize = 16
        axis.textColor = Color.parseColor("#03A9F4")
        data.axisXBottom = axis

        val yAxis = Axis()
        yAxis.name = "Movimientos"
        yAxis.textColor = Color.parseColor("#03A9F4")
        yAxis.textSize = 16
        data.axisYLeft = yAxis

        if(tipo.equals("Gasto")) {
            chart = findViewById(R.id.chart)

            chart.lineChartData = data
            val viewport = Viewport(chart.maximumViewport)
            viewport.top = 2000f
            chart.maximumViewport = viewport
            chart.currentViewport = viewport
        }else{
            chart2 = findViewById(R.id.chart2)

            chart2.lineChartData = data
            val viewport = Viewport(chart2.maximumViewport)
            viewport.top = 2000f
            chart2.maximumViewport = viewport
            chart2.currentViewport = viewport
        }

    }
}