package com.example.ejemplo1_curso_kotlin.Fragmentos

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import com.example.ejemplo1_curso_kotlin.R

class BaseFragmentos : AppCompatActivity(), Fragmento.NombreListener {

/*    var nombreActual:TextView? = null
    var bAdd: Button? = null
    var bReplace: Button? = null
*/


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_fragmento)



/*     Agregar dinamicamente fragmentos y reemplazarlos, ademas de hacer una accion

        nombreActual = findViewById(R.id.tvNombre)
        bAdd = findViewById(R.id.bAdd)
        bReplace = findViewById(R.id.bReplace)

        bAdd?.setOnClickListener{
            val fragmentManager = supportFragmentManager
            val fragmentTransaction = fragmentManager.beginTransaction()

            val nuevoFragmento = Fragmento()
            fragmentTransaction.add(R.id.container, nuevoFragmento)
            fragmentTransaction.addToBackStack(null)
            fragmentTransaction.commit()
        }

        bReplace?.setOnClickListener {
            val fragmentManager = supportFragmentManager
            val fragmentTransaction = fragmentManager.beginTransaction()

            val nuevoFragmento = Fragmento2()
            fragmentTransaction.replace(R.id.container, nuevoFragmento)
            fragmentTransaction.addToBackStack(null)
            fragmentTransaction.commit()
        }
*/
    }


/*
    override fun obtenerNombre(nombre: String) {
        super.obtenerNombre(nombre)

        nombreActual?.text = nombre
    }
    
  */
}
