package com.example.ejemplo1_curso_kotlin.Contactos

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.example.ejemplo1_curso_kotlin.R
import kotlinx.android.synthetic.main.template_contacto.view.*

class AdaptadorContacto (var contexto: Context, items:ArrayList<Contacto>):BaseAdapter(){

    var items:ArrayList<Contacto>? = null
    var itemsCopia:ArrayList<Contacto>? = null

    init {
        this.items = ArrayList(items)
        this.itemsCopia = items
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var viewHolder:ViewHolder? = null
        var vista:View? = convertView

        if (vista == null){
            vista = LayoutInflater.from(contexto).inflate(R.layout.template_contacto, null)
            viewHolder = ViewHolder(vista)
            vista.tag = viewHolder
        }else{
            viewHolder = vista.tag as? ViewHolder
        }

        var item = getItem(position) as Contacto
        viewHolder?.nombre?.text = item.nombre + " " + item.apellido
        viewHolder?.foto?.setImageResource(item.foto)
        viewHolder?.empresa?.text = item.empresa

        return vista!!

    }

    override fun getItem(position: Int): Any {
        Log.d("Res position", position.toString())
        return this.items?.get(position)!!
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getCount(): Int {
        return this.items?.count()!!
    }

    fun addItem(item:Contacto){
        itemsCopia?.add(item)
        items = ArrayList(itemsCopia!!)
        notifyDataSetChanged()
    }

    fun removeItem(index:Int){
        itemsCopia?.removeAt(index)
        items = ArrayList(itemsCopia!!)
        notifyDataSetChanged()
    }

    fun updateItem(index:Int, item:Contacto){
        itemsCopia?.set(index, item)
        items = ArrayList(itemsCopia!!)
        notifyDataSetChanged()
    }

    fun filtrar(texto:String){
        items?.clear()

        if (texto.isEmpty()){
            items = ArrayList(itemsCopia!!)
            notifyDataSetChanged()
            return
        }

        var busqueda = texto
        busqueda = busqueda.toLowerCase()

        for (item in itemsCopia!!){
            val nombre = item.nombre.toLowerCase()
            val apellido = item.apellido.toLowerCase()

            if (nombre.contains(busqueda) || apellido.contains(busqueda)){
                items?.add(item)
            }
        }

        notifyDataSetChanged()
    }

    private class ViewHolder(vista:View){
        var nombre:TextView? = null
        var foto:ImageView? = null
        var empresa:TextView? = null
        init {
            nombre = vista.findViewById(R.id.tvNombre) as TextView?
            foto = vista.findViewById(R.id.ivFoto) as ImageView?
            empresa = vista.findViewById(R.id.tvEmpresa) as TextView?
        }
    }

}