package com.example.organizapp

data class Movimiento(var nombre:String, var tipo: Int, var importe: Double, var fecha:String) {
    //tipo: 1->Gasto; 2->Ahorro
}