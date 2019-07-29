package com.example.ejemplo1_curso_kotlin.Fragmentos


import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

import com.example.ejemplo1_curso_kotlin.R
import kotlin.ClassCastException

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 *
 */
class Fragmento : Fragment() {

    var listener:NombreListener? = null
    var boton:Button? = null
    var nombre:EditText? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_fragmento, container, false)

        boton = view.findViewById(R.id.boton) as Button?
        nombre = view.findViewById(R.id.etNombre) as EditText?

        boton?.setOnClickListener{
            Toast.makeText(view.context, nombre?.text.toString(), Toast.LENGTH_LONG).show()
            listener?.obtenerNombre(nombre?.text.toString())
        }

        return view
    }

    interface NombreListener{

        fun obtenerNombre(nombre:String){

        }

    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        try {
            listener = context as NombreListener
        }catch (e: ClassCastException){
            throw ClassCastException(context.toString() + "Debe implementar la interfaz")
        }
    }
}
