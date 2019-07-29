package com.example.ejemplo1_curso_kotlin.Fragmentos

import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import com.example.ejemplo1_curso_kotlin.R

class Detalle : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detalle)

        if (resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE){
            finish()
            return
        }

        if (savedInstanceState == null){
            val fDetalle = ContenidoPeliculas()
            fDetalle.arguments = intent.extras
            supportFragmentManager.beginTransaction().add(R.id.container, fDetalle).commit()
        }


        /*
        val index = intent.getIntExtra("INDEX", 0)

        val foto = findViewById<ImageView>(R.id.ivFoto)

        foto.setImageResource(ListadoPeliculas.peliculas?.get(index)?.imagen!!)
        */
    }
}
