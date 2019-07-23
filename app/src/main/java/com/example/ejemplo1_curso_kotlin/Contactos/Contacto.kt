package com.example.ejemplo1_curso_kotlin.Contactos

class Contacto (nombre:String, apellido:String, empresa:String, edad:Int, peso:Float, direccion:String, email:String, foto:Int, telefono:String){
    var nombre:String = ""
    var apellido:String = ""
    var empresa:String = ""
    var edad:Int = 0
    var peso:Float = 0.0F
    var direccion:String = ""
    var email:String = ""
    var foto:Int = 0
    var telefono:String = ""

    init {
        this.nombre = nombre
        this.apellido = apellido
        this.empresa = empresa
        this.edad = edad
        this.peso = peso
        this.direccion = direccion
        this.email = email
        this.foto = foto
        this.telefono = telefono
    }

}