package com.example.organizapp

import android.view.View
import android.widget.CheckBox
import android.widget.LinearLayout
import android.widget.RadioButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class MiViewHolder (itemView: View) : RecyclerView.ViewHolder(itemView) {
    var nombre: TextView =itemView.findViewById(R.id.itTxtNombre)
    var gasto: RadioButton = itemView.findViewById(R.id.rbGasto)
    var ahorro: RadioButton = itemView.findViewById(R.id.rbAhorro)
    var importe: TextView =itemView.findViewById(R.id.txtImporte)
    var fecha: TextView = itemView.findViewById(R.id.txtFecha)
    var mainLayout : LinearLayout = itemView.findViewById(R.id.mainLayout)
}