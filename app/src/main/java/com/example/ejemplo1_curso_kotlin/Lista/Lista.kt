package com.example.ejemplo1_curso_kotlin.Lista

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.AdapterView.OnItemClickListener
import android.widget.ListView
import android.widget.Toast
import com.example.ejemplo1_curso_kotlin.R

class Lista : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.lista)

        var frutas:ArrayList<Fruta> = ArrayList()
        frutas.add(Fruta("Manzana", R.drawable.abc_ic_star_half_black_16dp))
        frutas.add(Fruta("Naranja", R.drawable.abc_ic_star_half_black_16dp))
        frutas.add(Fruta("Banana", R.drawable.abc_ic_arrow_drop_right_black_24dp))
        frutas.add(Fruta("Pera", R.drawable.abc_ic_go_search_api_material))

        var lista = findViewById<ListView>(R.id.lista)
        val adaptador = AdaptadorCustom(this, frutas)
        lista.adapter = adaptador
        lista.onItemClickListener = OnItemClickListener { parent, view, position, id ->
            Log.d("Res", position.toString() + " - " +  frutas.get(position).nombre)
            Toast.makeText(this, frutas.get(position).nombre, Toast.LENGTH_LONG).show()
        }
    }
}
