package com.example.organizapp

/*

Proyecto realizado por: Antonio, Paula y Denisa

Descripción: Clase del objeto Movimiento, utilizado para guardar la información y poder ser tratada a en la aplicación.

*/

data class Movimiento(var nombre:String, var tipo: Int, var importe: Double, var fecha:String) {
    //tipo: 1->Gasto; 2->Ahorro
}