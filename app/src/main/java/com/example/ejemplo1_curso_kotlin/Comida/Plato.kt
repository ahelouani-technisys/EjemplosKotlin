package com.example.ejemplo1_curso_kotlin.Comida

class Plato(nombre:String, precio:Double, rating:Float, foto:Int) {
    var nombre = ""
    var precio = 0.0
    var rating = 0.0f
    var foto = 0

    init {
        this.nombre = nombre
        this.precio = precio
        this.rating = rating
        this.foto = foto
    }
}