package com.example.organizapp

import android.app.Activity
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.recyclerview.widget.RecyclerView

class RecyclerviewAdapter (lista: ArrayList<Movimiento>, context: Context) :
    RecyclerView.Adapter<MiViewHolder>() {

    val context = context
    val lista = lista


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MiViewHolder {
        val inflater = LayoutInflater.from(context)
        val view: View = inflater.inflate(R.layout.recyclerview_item, parent, false)
        return MiViewHolder(view)
    }

    override fun onBindViewHolder(holder: MiViewHolder, position: Int) {
        //TextView nombre
        holder.nombre.text = lista[position].nombre
        //Radio button
        when (lista[position].tipo) {
            1 -> { holder.gasto.isChecked = true }
            2 -> { holder.ahorro.isChecked = true }
        }
        //TextView importe
        holder.importe.text = "Importe: "+lista[position].importe.toString()
        //TextView fecha
        holder.fecha.text = "Fecha: "+lista[position].fecha.toString()

        //Animate Recyclerview
        val translateAnim = AnimationUtils.loadAnimation(
            context, R.anim.translate_anim
        )
        holder.mainLayout.animation = translateAnim

    }

    override fun getItemCount(): Int {
        return lista.size
    }


}