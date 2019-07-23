package com.example.ejemplo1_curso_kotlin.Contactos

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.*
import com.example.ejemplo1_curso_kotlin.R
import androidx.appcompat.widget.Toolbar


class ListaContactos : AppCompatActivity() {

    var lista:ListView? = null
    var grid:GridView? = null
    var viewSwitcher:ViewSwitcher? = null


    companion object{
        var contactos: ArrayList<Contacto>? = null
        var adaptador:AdaptadorContacto? = null
        var adaptadorGrid:AdaptadorContactoGrid? = null

        fun agregarContacto(contacto:Contacto){
            adaptador?.addItem(contacto)
        }

        fun obtenerContacto(index: Int): Contacto {
            return adaptador?.getItem(index) as Contacto
        }

        fun EliminarContacto(index: Int) {
            adaptador?.removeItem(index)
        }

        fun actualizarContacto(index:Int, nuevoContacto: Contacto) {
            adaptador?.updateItem(index, nuevoContacto)
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.lista_contactos)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        contactos = ArrayList()
        contactos?.add(Contacto("Marcos","Fernandez", "Tech",23,80.6f,"Fake 123","a@a.com", R.drawable.abc_ic_star_black_48dp,"1544446665"))
        contactos?.add(Contacto("Laura","Fernandez", "Tech",23,80.6f,"Fake 123","a@a.com", R.drawable.design_fab_background,"1544446665"))
        contactos?.add(Contacto("Gaston","Mnches", "Tech",23,80.6f,"Fake 123","a@a.com", R.drawable.abc_tab_indicator_material,"1544446665"))
        contactos?.add(Contacto("Brian","Guty", "Tech",23,80.6f,"Fake 123","a@a.com", R.drawable.abc_ic_arrow_drop_right_black_24dp,"1544446665"))
        contactos?.add(Contacto("Juan","Valdez", "Tech",23,80.6f,"Fake 123","a@a.com", R.drawable.abc_btn_check_to_on_mtrl_000,"1544446665"))


        lista = findViewById<ListView>(R.id.lista)
        grid = findViewById<GridView>(R.id.grid_contacto)

        adaptador = AdaptadorContacto(this,contactos!!)
        adaptadorGrid = AdaptadorContactoGrid(this, contactos!!)

        viewSwitcher = findViewById(R.id.viewSwitcher)

        lista?.adapter = adaptador
        lista?.setOnItemClickListener { parent, view, position, id ->
            val intent = Intent(this,DetalleContacto::class.java)
            intent.putExtra("ID", position.toString())
            startActivity(intent)
        }
        grid?.adapter = adaptadorGrid
        grid?.setOnItemClickListener { parent, view, position, id ->
            val intent = Intent(this,DetalleContacto::class.java)
            intent.putExtra("ID", position.toString())
            startActivity(intent)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)

        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val itemBusqueda = menu?.findItem(R.id.searchView)
        var searchView = itemBusqueda?.actionView as SearchView

        val itemSwitch = menu?.findItem(R.id.switchView)
        itemSwitch?.setActionView(R.layout.switch_item)
        val switchView = itemSwitch?.actionView?.findViewById(R.id.sCambiaVista) as Switch?

        switchView?.setOnCheckedChangeListener{ buttonView, isChecked ->
            viewSwitcher?.showNext()
        }

        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        searchView.queryHint = "Buscar contaxto..."
        searchView.setOnQueryTextFocusChangeListener {v, hasFocus ->
            //preparar datos
        }

        searchView.setOnQueryTextListener(object: SearchView.OnQueryTextListener{
            override fun onQueryTextChange(nexText: String?): Boolean {
                //filtrar
                adaptador?.filtrar(nexText!!)
                return true
            }

            override fun onQueryTextSubmit(nexText: String?): Boolean {
                //filtrar

                return true
            }
        })
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item?.itemId){
            R.id.iNuevo ->{
                val intent = Intent(this, NuevoContacto::class.java)
                startActivity(intent)
                return true
            }else ->{ return super.onOptionsItemSelected(item)}
        }
    }

    override fun onResume() {
        super.onResume()

        adaptador?.notifyDataSetChanged()
    }
}
