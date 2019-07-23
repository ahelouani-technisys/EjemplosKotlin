package com.example.ejemplo1_curso_kotlin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import com.example.ejemplo1_curso_kotlin.Clima.WeatherApiService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class MainActivity : AppCompatActivity() {
    var disposable: Disposable? = null

    val weatherApiServ by lazy {
        WeatherApiService.create()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val tvGrados = findViewById<TextView>(R.id.tvGrados)
        val tvCiudad = findViewById<TextView>(R.id.tvCiudad)
        val tvClima = findViewById<TextView>(R.id.tvClima)

        val ciudad = intent.getStringExtra(R.string.tag_ciudades_ciudad.toString())
        disposable = weatherApiServ.ObtenerClima(ciudad, getString(R.string.api_clima_appid),"metric", "es")

            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { result ->
                    tvGrados.text = (result.main!!.temp).toInt().toString() + "°"
                    tvCiudad.text = result.name
                    tvClima.text = result.weather!!.get(0).description
                },
//                { result -> Toast.makeText(this, "${result.query.searchinfo.totalhits} result found", Toast.LENGTH_LONG).show() },
                { error -> Toast.makeText(this, error.message, Toast.LENGTH_SHORT).show() }
            )

        /*val sAceptar = findViewById<Button>(R.id.bAceptar)
        val etNombre = findViewById<EditText>(R.id.etNombre)
        val cbCheck = findViewById<CheckBox>(R.id.cbCheck)
        sAceptar.setOnClickListener(View.OnClickListener {
            if (ValidarDatos()) {
                var textoaMostrar:String = "Hola " + etNombre.text
                if (cbCheck.isChecked){
                    textoaMostrar += ", has checkeado :P"
                }

                Toast.makeText(this, textoaMostrar, Toast.LENGTH_LONG).show()
            }
        })
        */

    }
    override fun onPause() {
        super.onPause()
        disposable?.dispose()
    }
/*
    fun ValidarDatos() :Boolean{
        val etNombre = findViewById<EditText>(R.id.etNombre)
        val nombreUsuario = etNombre.text
        if (nombreUsuario.isNullOrEmpty()){
            return false
        }
        return true
    }
*/
}
