package com.example.ejemplo1_curso_kotlin.Fragmentos

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.example.ejemplo1_curso_kotlin.R

class Base_Fragmentos : AppCompatActivity(), Fragmento.NombreListener {

    var nombreActual:TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.base__fragmentos)
    }
    
    
}
