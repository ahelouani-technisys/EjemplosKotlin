package com.example.ejemplo1_curso_kotlin.Contactos

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.ArrayAdapter
import android.widget.ImageView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.Toolbar
import com.example.ejemplo1_curso_kotlin.R
import kotlinx.android.synthetic.main.nuevo_contacto.*


class NuevoContacto : AppCompatActivity() {

    var fotoIndex:Int = 0
    val fotos = arrayOf(R.drawable.abc_ic_star_black_48dp, R.drawable.ic_launcher_background,R.drawable.ic_search_black_24dp)
    var foto:ImageView? = null
    var index:Int? = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.nuevo_contacto)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        var actionBar = supportActionBar
        actionBar?.setDisplayHomeAsUpEnabled(true)

        foto = findViewById<ImageView>(R.id.ivFoto)
        foto?.setOnClickListener{
            seleccionarFoto()
        }

        if (intent.hasExtra("ID")){
            index = intent.getStringExtra("ID").toInt()
            RellenarInfo(index!!)
        }
    }

    private fun RellenarInfo(index: Int) {
        val contacto = ListaContactos.obtenerContacto(index)

        tvNombre.setText(contacto.nombre)
        tvApellido.setText(contacto.apellido)
        tvEmpresa.setText(contacto.empresa)
        tvEdad.setText(contacto.edad.toString())
        tvPeso.setText(contacto.peso.toString())
        tvEmail.setText(contacto.email)
        tvDireccion.setText(contacto.direccion)
        tvTelefono.setText(contacto.telefono)
        ivFoto.setImageResource(contacto.foto)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_nuevo, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item?.itemId){

            android.R.id.home -> {
                finish()
                return true
            }

            R.id.iCrearNuevo ->{
                val contacto = Contacto(
                    tvNombre.text.toString(),
                    tvApellido.text.toString(),
                    tvEmpresa.text.toString(),
                    tvEdad.text.toString().toInt(),
                    tvPeso.text.toString().toFloat(),
                    tvDireccion.text.toString(),
                    tvEmail.text.toString(),
                    obtenerFoto(fotoIndex),
                    tvTelefono.text.toString()
                )
                if (index != -1) {
                    ListaContactos.actualizarContacto(index!!,contacto)
                }else{
                    ListaContactos.agregarContacto(contacto)
                }
                finish()
                Log.d("N° Elementos", ListaContactos.contactos?.count().toString())
                return true
            }else ->{ return super.onOptionsItemSelected(item)}
        }
    }

    private fun seleccionarFoto() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Selecciona una imagen de perfil")

        val adaptadorDialogo = ArrayAdapter<String>(this,android.R.layout.simple_selectable_list_item)
        adaptadorDialogo.add("Estrella")
        adaptadorDialogo.add("Android")
        adaptadorDialogo.add("Lupa")

        builder.setAdapter(adaptadorDialogo){
                dialog, which ->
            fotoIndex = which
            foto?.setImageResource(obtenerFoto(fotoIndex))
        }

        builder.setNegativeButton("Cancelar"){
            dialog, which ->
            dialog.dismiss()
        }

        builder.show()
    }

    private fun obtenerFoto(index: Int):Int{
        return fotos.get(index)
    }

}
