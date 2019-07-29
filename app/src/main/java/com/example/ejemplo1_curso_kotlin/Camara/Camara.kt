package com.example.ejemplo1_curso_kotlin.Camara

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.os.Environment.getExternalStorageDirectory
import android.provider.MediaStore
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.app.ActivityCompat.startActivityForResult
import androidx.core.content.FileProvider
import com.example.ejemplo1_curso_kotlin.R
import java.text.SimpleDateFormat
import java.io.File
import java.util.*

class Camara : AppCompatActivity() {

    var ivFoto:ImageView? = null
    var foto:Fotos? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_camara)

        val bFoto = findViewById<Button>(R.id.bFoto)
        ivFoto = findViewById<ImageView>(R.id.ivFoto)

        foto = Fotos(this, ivFoto!!)

        bFoto.setOnClickListener {
            foto?.TomarFoto()
        }
        val bSeleccionar = findViewById<Button>(R.id.bSeleccionar)

        bSeleccionar.setOnClickListener {

            foto?.seleccionarFoto()
        }

    }




    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        foto?.requestPermissionsResult(requestCode, permissions, grantResults)
    }





    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        foto?.activityResult(requestCode, resultCode, data)

    }


}