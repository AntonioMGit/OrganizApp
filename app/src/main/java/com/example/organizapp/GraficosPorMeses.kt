package com.example.organizapp

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.FirebaseFirestore
import lecho.lib.hellocharts.model.*
import lecho.lib.hellocharts.view.LineChartView
import java.util.ArrayList

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

        var anio = "2022"

        buscarEnAnio(anio, "Gasto")

        buscarEnAnio(anio, "Ahorro")

    }

    private fun buscarEnAnio(anio: String, tipo:String){

        val db = FirebaseFirestore.getInstance()
        val gastos = db.collection("Usuarios").document("aa@aa.aa") .collection(tipo.toString()) //cambiar la key por Login.keyUser
        var fechaSeparada: List<String>

        var gastosArray: IntArray = intArrayOf(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0)

        //problema con esta cosa, es un hilo, con lo que si quieres que devuelva lo que haces aqui tienes que esperar a que termine
        //si tiras normal el programa sigue y no coge los datos porque esto va mas rapido
        //entonces hay que hacer las cosas cuando el hilo termine
        gastos.get().addOnSuccessListener { documents ->
            for (document in documents) {
                //Log.d("noseque", "${document.id} => ${document.data}")

                fechaSeparada = document.data.get("Fecha").toString().split("/")

                var numAnio= fechaSeparada.get(2)
                var numMes= fechaSeparada.get(1)
                //Toast.makeText(applicationContext, "Mes: " + numMes, Toast.LENGTH_LONG).show()
                if(numAnio.equals(anio.toString())) {
                    //Log.d("noseque", "pasa")
                    //pieData.add(SliceValue(54f, Color.RED).setLabel(document.data.get("Nombre").toString() + ": " + document.get("Importe").toString()))
                    gastosArray[numMes.toInt()] += document.data.get("Importe").toString().toInt()
                }
            }

            crearGrafico(gastosArray, tipo)
        }

    }
    //hay que hacerlo asi porque sino el hilo tarda más y no se pinta nada
    //hasta que no termine de leer los datos y hacer las cosas con ellos para poder ponerlos en las graficas no se puede hacer nadas
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
        yAxis.name = "Sales in millions"
        yAxis.textColor = Color.parseColor("#03A9F4")
        yAxis.textSize = 16
        data.axisYLeft = yAxis

        if(tipo.equals("Gasto")) {
            chart = findViewById(R.id.chart)

            chart.lineChartData = data
            val viewport = Viewport(chart.maximumViewport)
            viewport.top = 110f
            chart.maximumViewport = viewport
            chart.currentViewport = viewport
        }else{
            chart2 = findViewById(R.id.chart2)

            chart2.lineChartData = data
            val viewport = Viewport(chart2.maximumViewport)
            viewport.top = 110f
            chart2.maximumViewport = viewport
            chart2.currentViewport = viewport
        }

    }
}