package com.example.ejemplo1_curso_kotlin.Comida

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.appcompat.view.ActionMode
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.ejemplo1_curso_kotlin.R

class Comidas : AppCompatActivity() {

    var lista:RecyclerView? = null
    var adaptador:AdaptadorComidas? = null
    var layoutManager:RecyclerView.LayoutManager? = null
    var isActionMode = false
    var actionMode:ActionMode? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.comidas)

        var platos = ArrayList<Plato>()
        platos.add(Plato("Plato1",250.0,3.5f, R.drawable.plato1))
        platos.add(Plato("Plato2",250.0,3.5f, R.drawable.plato2))
        platos.add(Plato("Plato3",250.0,3.5f, R.drawable.plato3))
        platos.add(Plato("Plato4",250.0,3.5f, R.drawable.plato4))
        platos.add(Plato("Plato5",250.0,3.5f, R.drawable.plato5))
        platos.add(Plato("Plato6",250.0,3.5f, R.drawable.plato6))
        platos.add(Plato("Plato7",250.0,3.5f, R.drawable.plato7))
        platos.add(Plato("Plato8",250.0,3.5f, R.drawable.plato8))

        lista = findViewById(R.id.rvLista)
        lista?.setHasFixedSize(true)

        layoutManager = LinearLayoutManager(this)
        lista?.layoutManager = layoutManager


        val callback = object: ActionMode.Callback{
            override fun onActionItemClicked(mode: ActionMode?, item: MenuItem?): Boolean {
                when(item?.itemId){
                    R.id.iEliminar -> {
                        adaptador?.eliminarSeleccionados()
                    }
                }

                adaptador?.terminarActionMode()
                mode?.finish()
                isActionMode = false
                return true
            }

            override fun onCreateActionMode(mode: ActionMode?, menu: Menu?): Boolean {
                adaptador?.iniciarActionMode()
                actionMode = mode

                menuInflater.inflate(R.menu.menu_comida_contextual, menu!!)
                return true
            }

            override fun onPrepareActionMode(mode: ActionMode?, menu: Menu?): Boolean {
                return true
            }

            override fun onDestroyActionMode(mode: ActionMode?) {
                adaptador?.destruirActionMode()
                isActionMode = false
            }

        }

        adaptador = AdaptadorComidas(platos, object: ClickListener {
            override fun onClick(vista: View, index: Int) {
                Toast.makeText(applicationContext, platos.get(index).nombre, Toast.LENGTH_LONG).show()
            }
        },  object: LongClickListener{
            override fun longClick(vista: View, index: Int) {
                if (!isActionMode){
                    isActionMode = true
                    startSupportActionMode(callback)
                }else{
                    //seleccionar - sacar seleccion
                }
                adaptador?.seleccionarItem(index)
                actionMode?.title = adaptador?.obtenerCantidadElementosSeleccionados().toString() + " seleccionados"
            }
        })
        lista?.adapter = adaptador

        val swipeToRefresh = findViewById<SwipeRefreshLayout>(R.id.swipeToRefresh)
        swipeToRefresh.setOnRefreshListener {
            adaptador?.addItem(Plato("Plato1",250.0,3.5f, R.drawable.plato1))
            swipeToRefresh.isRefreshing = false
        }
    }
}
