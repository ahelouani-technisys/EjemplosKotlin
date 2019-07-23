package com.example.ejemplo1_curso_kotlin.Comida

import android.content.Context
import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.ejemplo1_curso_kotlin.R

class AdaptadorComidas(items:ArrayList<Plato>, var listener: ClickListener, var longClickListener: LongClickListener):RecyclerView.Adapter<AdaptadorComidas.ViewHolder>() {

    var items: ArrayList<Plato>? = null
    var multiSeleccion: Boolean = false

    var itemsSeleccionados:ArrayList<Int>? = null
    var viewHolder: ViewHolder? = null

    init {
        this.items = items
        itemsSeleccionados = ArrayList()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val vista = LayoutInflater.from(parent.context).inflate(R.layout.template_plato, parent,false)
        viewHolder = ViewHolder(vista, listener, longClickListener)

        return viewHolder!!
    }

    override fun getItemCount(): Int {
        return items?.count()!!
    }

    override fun onBindViewHolder(holder: AdaptadorComidas.ViewHolder, position: Int) {
        val item = items?.get(position)
        holder.foto?.setImageResource(item?.foto!!)
        holder.nombre?.text = item?.nombre
        holder.precio?.text = "$ " + item?.precio.toString()
        holder.rating?.rating = item?.rating!!

        if(itemsSeleccionados?.contains(position)!!){
            holder.vista.setBackgroundColor(Color.CYAN)
        }else{
            holder.vista.setBackgroundColor(Color.WHITE)
        }
    }

    fun terminarActionMode() {

        for (item in itemsSeleccionados!!){
            itemsSeleccionados?.remove(item)
        }
        multiSeleccion = false
        notifyDataSetChanged()
    }

    fun destruirActionMode() {
        itemsSeleccionados?.clear()
        multiSeleccion = false
        notifyDataSetChanged()
    }

    fun iniciarActionMode() {

        multiSeleccion = true
    }

    fun addItem(item:Plato){
        items?.add(item)
        notifyDataSetChanged()
    }

    fun seleccionarItem(index:Int){
        if (multiSeleccion){
            if (itemsSeleccionados?.contains(index)!!){
                itemsSeleccionados?.remove(index)

            }else{
                itemsSeleccionados?.add(index)
            }
            notifyDataSetChanged()
        }
    }

    fun obtenerCantidadElementosSeleccionados(): Int {
        return itemsSeleccionados?.count()!!
    }

    fun eliminarSeleccionados() {
        if (itemsSeleccionados?.count()!! > 0){
            var itemsEliminados = ArrayList<Plato>()
            for (index in itemsSeleccionados!!){
                itemsEliminados.add(items?.get(index)!!)
            }

            items?.removeAll(itemsEliminados)
            itemsSeleccionados?.clear()
        }
        notifyDataSetChanged()
    }

    class ViewHolder(vista: View, listener: ClickListener, longClickListener: LongClickListener): RecyclerView.ViewHolder(vista), View.OnClickListener, View.OnLongClickListener{
        var listener:ClickListener? = null
        var longLisener:LongClickListener? = null
        var vista = vista
        var foto:ImageView? = null
        var nombre:TextView? = null
        var precio:TextView? = null
        var rating:RatingBar? = null
        init {
            foto = vista.findViewById(R.id.ivFoto) as ImageView?
            nombre = vista.findViewById(R.id.tvNombre) as TextView?
            precio = vista.findViewById(R.id.tvPrecio) as TextView?
            rating = vista.findViewById(R.id.tvRating) as RatingBar?
            this.listener = listener
            this.longLisener = longClickListener
            vista.setOnClickListener(this)
            vista.setOnLongClickListener(this)
        }

        override fun onClick(v: View?) {
            this.listener?.onClick(v!!,adapterPosition)
        }
        override fun onLongClick(v: View?):Boolean {
            this.longLisener?.longClick(v!!,adapterPosition)
            return true
        }
    }
}
