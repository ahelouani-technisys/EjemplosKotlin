package com.example.ejemplo1_curso_kotlin.Fragmentos


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView

import com.example.ejemplo1_curso_kotlin.R

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 *
 */
class ContenidoPeliculas : Fragment() {

    var vista:View? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        vista = inflater.inflate(R.layout.fragment_contenido_peliculas, container, false)
        cambiarFoto()
        return vista
    }

    fun nuevaInstancia(index: Int): ContenidoPeliculas{
        val f = ContenidoPeliculas()

        val args = Bundle()
        args.putInt("INDEX", index)
        f.arguments = args

        return f
    }

    fun obtenerIndex(): Int {
        val index = arguments?.getInt("INDEX",0)!!
        return index
    }

    private fun cambiarFoto() {
        val foto = vista!!.findViewById(R.id.ivFoto) as ImageView
        foto.setImageResource(ListadoPeliculas.peliculas?.get(obtenerIndex())?.imagen!!)
    }

}
