package com.example.ejemplo1_curso_kotlin.CicloDeVida

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.widget.Button
import android.widget.Toast
import com.example.ejemplo1_curso_kotlin.R

class Ciclo : AppCompatActivity() {
    var nombre = "Juan"
    val NOMBRE = "nombre"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.ciclo)

        val boton = findViewById<Button>(R.id.boton)
        Toast.makeText(this,nombre, Toast.LENGTH_SHORT).show()

        boton.setOnClickListener {
            nombre = "Manuel"
            Toast.makeText(this,nombre, Toast.LENGTH_SHORT).show()
        }
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        nombre = savedInstanceState?.getString(NOMBRE)!!
        Toast.makeText(this,nombre, Toast.LENGTH_SHORT).show()

    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState?.putString(NOMBRE, nombre)
        //Cuando giro la pantalla, se destruye y vuelve a crear, asi guardo el nombre para cuando se recrea
    }

    override fun onResume() {
        super.onResume()
        Toast.makeText(this,"onResume - Visible", Toast.LENGTH_SHORT).show()
    }

    override fun onPause() {
        super.onPause()
        Toast.makeText(this,"onPause - Preparando para ocultar", Toast.LENGTH_SHORT).show()

    }

    override fun onStop() {
        super.onStop()
        Toast.makeText(this,"onStop - Oculto", Toast.LENGTH_SHORT).show()
    }

    override fun onDestroy() {
        super.onDestroy()
        Toast.makeText(this,"Destruyendo app", Toast.LENGTH_SHORT).show()
    }


}
