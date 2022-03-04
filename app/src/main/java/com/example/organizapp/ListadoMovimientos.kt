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

/*

Proyecto realizado por: Antonio, Paula y Denisa

Descripción: Clase encargada de mostrar los gastos e ingresos de un usuario en forma de lista.
    Recoge los datos de Firebase y los pinta mediante RecyclerView.

*/

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

        obtenerGastos()

    }

    private fun obtenerGastos(){

        val db = FirebaseFirestore.getInstance()

        val gastos = db.collection("Usuarios").document(Login.keyUser) .collection("Gasto")

        gastos.get().addOnSuccessListener { documents ->
            for (document in documents) {
                if(!document.data.get("Fecha").toString().equals("0/0/0")) {
                    var m = Movimiento(document.data.get("Nombre").toString(), 1, document.get("Importe").toString().toDouble(), document.get("Fecha").toString())
                    lista.add(m)
                }
            }

            obtenerAhorros()
        }

    }

    private fun obtenerAhorros() {

        val db = FirebaseFirestore.getInstance()

        val ingresos = db.collection("Usuarios").document(Login.keyUser) .collection("Ahorro")

        ingresos.get().addOnSuccessListener { documents ->
            for (document in documents) {
                if(!document.data.get("Fecha").toString().equals("0/0/0")) {
                    var m = Movimiento(document.data.get("Nombre").toString(), 2, document.get("Importe").toString().toDouble(), document.get("Fecha").toString())
                    lista.add(m)
                }
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