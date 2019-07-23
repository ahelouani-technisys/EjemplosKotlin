package com.example.ejemplo1_curso_kotlin.Clima
import com.google.gson.annotations.SerializedName

class Ciudad (name: String, weather:ArrayList<Weather>, main:Main) {
    var name:String = ""
    @SerializedName("weather")
    var weather = ArrayList<Weather>()
    var main:Main? = null

    init {
        this.name = name
        this.weather = weather
        this.main = main
    }
}