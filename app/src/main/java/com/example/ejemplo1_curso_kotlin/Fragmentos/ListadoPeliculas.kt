package com.example.ejemplo1_curso_kotlin.Fragmentos


import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.FrameLayout
import android.widget.ListView
import android.widget.Toast
import androidx.fragment.app.FragmentTransaction

import com.example.ejemplo1_curso_kotlin.R

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class ListadoPeliculas : Fragment() {

    companion object{
        var peliculas:ArrayList<Pelicula>? = null
    }

    private var posicionActual:Int? = 0
    var nombrePeliculas:ArrayList<String>? = null
    var lista:ListView? = null
    var hayDoblePanel:Boolean = false

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        configurarListView()

        val frameDetalle = activity?.findViewById(R.id.detalle) as FrameLayout?
        hayDoblePanel = frameDetalle != null && frameDetalle.visibility == View.VISIBLE

        if (savedInstanceState != null){
            posicionActual = savedInstanceState.getInt("INDEX", 0)
        }

        if (hayDoblePanel){
            lista?.choiceMode = ListView.CHOICE_MODE_SINGLE
            mostrarDetalles(posicionActual!!)
        }
    }

    private fun configurarListView() {
        peliculas = ArrayList()
        peliculas?.add(Pelicula("Volver al futuro", R.drawable.plato1))
        peliculas?.add(Pelicula("Terminator", R.drawable.plato2))
        peliculas?.add(Pelicula("Avatar", R.drawable.plato3))
        peliculas?.add(Pelicula("Avengers", R.drawable.plato4))

        nombrePeliculas = obtenerNombrePeliculas(peliculas!!)

        val adaptador = ArrayAdapter(activity!!, android.R.layout.simple_list_item_activated_1, nombrePeliculas!!)

        lista = activity?.findViewById(R.id.lista) as ListView?
        lista?.adapter = adaptador

        lista?.setOnItemClickListener { adapterView, view, i, l ->
            mostrarDetalles(i)
            //Toast.makeText(vista.context, nombrePeliculas?.get(i), Toast.LENGTH_LONG).show()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val vista = inflater.inflate(R.layout.fragment_listado_peliculas, container, false)


        return vista
    }
    private fun mostrarDetalles(index: Int){
        posicionActual = index

        if (hayDoblePanel){
            var fDetalle = activity?.supportFragmentManager?.findFragmentById(R.id.detalle) as? ContenidoPeliculas

            if (fDetalle == null || fDetalle.obtenerIndex() != index){
                fDetalle = ContenidoPeliculas().nuevaInstancia(index)

                val fragmentTransaction = activity!!.supportFragmentManager.beginTransaction()
                fragmentTransaction.replace(R.id.detalle, fDetalle)
                fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)

                fragmentTransaction.commit()
            }

        }else{
            val intent = Intent(activity, Detalle::class.java)
            intent.putExtra("INDEX", index)
            startActivity(intent)
        }
    }

    private fun obtenerNombrePeliculas(peliculas: ArrayList<Pelicula>): ArrayList<String>? {
        val nombres = ArrayList<String>()

        for (pelicula in peliculas){
            nombres.add(pelicula.nombre)
        }
        return nombres
    }

}
