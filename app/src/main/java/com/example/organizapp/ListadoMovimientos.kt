package com.example.organizapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class ListadoMovimientos : AppCompatActivity() {

    lateinit var recyclerView: RecyclerView
    lateinit var imgVacio: ImageView
    lateinit var txtSinDatos: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_listado_movimientos)

        recyclerView = findViewById(R.id.recyclerView)
        imgVacio = findViewById(R.id.imgVacio)
        txtSinDatos = findViewById(R.id.txtSinDatos)

        //Lista de usuarios
        var listaMovimientos = obtenerTodos()

        //RecyclerView
        recyclerView.adapter = RecyclerviewAdapter(listaMovimientos, this)
        recyclerView.layoutManager = LinearLayoutManager(this)

    }

    private fun obtenerTodos(): ArrayList<Movimiento> {
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
    }
}