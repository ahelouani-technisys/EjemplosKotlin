package com.example.ejemplo1_curso_kotlin.Contactos

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.widget.Toolbar
import com.example.ejemplo1_curso_kotlin.R
import kotlinx.android.synthetic.main.detalle_contacto.*

class DetalleContacto : AppCompatActivity() {

    var index:Int? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.detalle_contacto)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        var actionBar = supportActionBar
        actionBar?.setDisplayHomeAsUpEnabled(true)

        index = intent.getStringExtra("ID").toInt()
        Log.d("Res", index.toString())

        mapearDatos()

    }

    private fun mapearDatos() {
        var contacto: Contacto = ListaContactos.obtenerContacto(index!!)

        tvNombre.text = contacto.nombre + contacto.apellido
        tvEmpresa.text = contacto.empresa
        tvEdad.text = contacto.edad.toString() + " años"
        tvPeso.text = contacto.peso.toString() + " kg"
        tvEmail.text = contacto.email
        tvDireccion.text = contacto.direccion
        tvTelefono.text = contacto.telefono
        ivFoto.setImageResource(contacto.foto)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_detalle, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item?.itemId){

            android.R.id.home -> {
                finish()
                return true
            }

            R.id.iEditar -> {
                val intent = Intent(this, NuevoContacto::class.java)
                intent.putExtra("ID", index.toString())
                startActivity(intent)
                return true
            }

            R.id.iEliminar -> {
                ListaContactos.EliminarContacto(index!!)
                finish()
                return true

            }

            else ->{ return super.onOptionsItemSelected(item)}
        }
    }

    override fun onResume() {
        super.onResume()
        mapearDatos()
    }

}
