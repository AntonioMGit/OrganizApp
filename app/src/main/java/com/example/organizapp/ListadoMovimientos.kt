package com.example.organizapp

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore
import lecho.lib.hellocharts.model.PieChartData
import lecho.lib.hellocharts.model.SliceValue
import lecho.lib.hellocharts.view.PieChartView

class ListadoMovimientos : AppCompatActivity() {

    lateinit var recyclerView: RecyclerView
    lateinit var imgVacio: ImageView
    lateinit var txtSinDatos: TextView

    var lista = ArrayList<Movimiento>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_listado_movimientos)

        recyclerView = findViewById(R.id.recyclerView)
        imgVacio = findViewById(R.id.imgVacio)
        txtSinDatos = findViewById(R.id.txtSinDatos)

        //Lista de usuarios
        //var listaMovimientos = obtenerGastos()

        obtenerGastos()

    }

    private fun obtenerGastos(){
        /*
        var lista = ArrayList<Movimiento>()
        var m1 = Movimiento("Mov1", 1, 30.0, "2022-02-12")
        var m2 = Movimiento("Mov2", 1, 80.0, "2022-01-03")
        var m3 = Movimiento("Mov3", 2, 500.0, "2022-02-04")

        lista.add(m1)
        lista.add(m2)
        lista.add(m3)

        if(lista.isNotEmpty()){
            imgVacio.visibility = View.GONE
            txtSinDatos.visibility = View.GONE
        }
        else{
            imgVacio.visibility = View.VISIBLE
            txtSinDatos.visibility = View.VISIBLE
        }

        return lista
         */

        val db = FirebaseFirestore.getInstance()

        val gastos = db.collection("Usuarios").document(Login.keyUser) .collection("Gasto")


        gastos.get().addOnSuccessListener { documents ->
            for (document in documents) {
                var m = Movimiento(document.data.get("Nombre").toString(), 1, document.get("Importe").toString().toDouble(), document.get("Fecha").toString())
                lista.add(m)
            }

            obtenerAhorros()
        }


    }

    private fun obtenerAhorros() {

        val db = FirebaseFirestore.getInstance()

        val ingresos = db.collection("Usuarios").document(Login.keyUser) .collection("Ahorro")

        ingresos.get().addOnSuccessListener { documents ->
            for (document in documents) {
                var m = Movimiento(document.data.get("Nombre").toString(), 2, document.get("Importe").toString().toDouble(), document.get("Fecha").toString())
                lista.add(m)
            }

            pintarDatos()

        }
    }

    private fun pintarDatos() {
        if(lista.isNotEmpty()){
            imgVacio.visibility = View.GONE
            txtSinDatos.visibility = View.GONE
        }
        else{
            imgVacio.visibility = View.VISIBLE
            txtSinDatos.visibility = View.VISIBLE
        }

        //RecyclerView
        recyclerView.adapter = RecyclerviewAdapter(lista, this)
        recyclerView.layoutManager = LinearLayoutManager(this)

    }
}