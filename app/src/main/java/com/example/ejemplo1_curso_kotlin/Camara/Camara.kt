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

    val SOLICITUD_TOMAR_FOTO = 1
    val SOLICITUD_SELECCIONAR_FOTO = 2
    val permisoCamara = android.Manifest.permission.CAMERA
    val permisoWriteStorage = android.Manifest.permission.WRITE_EXTERNAL_STORAGE
    val permisoReadStorage = android.Manifest.permission.READ_EXTERNAL_STORAGE
    var ivFoto:ImageView? = null
    var urlFotoActual = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_camara)

        val bFoto = findViewById<Button>(R.id.bFoto)
        ivFoto = findViewById<ImageView>(R.id.ivFoto)

        bFoto.setOnClickListener {
            TomarFoto()
        }
        val bSeleccionar = findViewById<Button>(R.id.bSeleccionar)

        bSeleccionar.setOnClickListener {

            seleccionarFoto()
        }

    }

    private fun TomarFoto() {
        pedirPermisos()
    }

    private fun seleccionarFoto() {
        pedirPermisosSeleccionarFotos()

    }

    private fun pedirPermisos() {
        val deboProveerContexto = ActivityCompat.shouldShowRequestPermissionRationale(this, permisoCamara)

        if (deboProveerContexto) {
        }else{
        }
        solicitudPermiso()
    }

    private fun pedirPermisosSeleccionarFotos () {
        val deboProveerContexto = ActivityCompat.shouldShowRequestPermissionRationale(this, permisoCamara)

        if (deboProveerContexto) {
        }else{
        }
        solicitudPermisoSeleccionarFoto()
    }


    private fun solicitudPermiso() {
        ActivityCompat.requestPermissions(this,arrayOf(permisoCamara,permisoReadStorage, permisoWriteStorage), SOLICITUD_TOMAR_FOTO)
    }

    private fun solicitudPermisoSeleccionarFoto() {
        ActivityCompat.requestPermissions(this,arrayOf(permisoReadStorage), SOLICITUD_SELECCIONAR_FOTO)
    }


    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when(requestCode){
            SOLICITUD_TOMAR_FOTO ->{
                if (grantResults.isNotEmpty() &&
                    grantResults[0] == PackageManager.PERMISSION_GRANTED &&
                    grantResults[1] == PackageManager.PERMISSION_GRANTED &&
                    grantResults[2] == PackageManager.PERMISSION_GRANTED){
                    dispararIntentTomarFoto()
                }else{
                    Toast.makeText(this, "No diste permiso", Toast.LENGTH_LONG).show()
                }
            }

            SOLICITUD_SELECCIONAR_FOTO->{
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    dispararIntentSeleccionarFoto()
                }else{
                    Toast.makeText(this, "No diste permiso", Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    private fun dispararIntentTomarFoto() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        if (intent.resolveActivity(packageManager) != null){
            var archivoFoto:File? = null
            archivoFoto = crearArchivoImagen()

            if (archivoFoto!= null){
                val urlFoto = FileProvider.getUriForFile(this, "com.example.ejemplo1_curso_kotlin.android.fileprovider", archivoFoto)
                intent.putExtra(MediaStore.EXTRA_OUTPUT, urlFoto)
                startActivityForResult(intent, SOLICITUD_TOMAR_FOTO)

            }

        }
    }

    fun dispararIntentSeleccionarFoto(){
        val intent = Intent(Intent.ACTION_GET_CONTENT)

        intent.setType("image/*")

        startActivityForResult(Intent.createChooser(intent, "Seleccionar una foto"), SOLICITUD_SELECCIONAR_FOTO)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        when(requestCode){
            SOLICITUD_TOMAR_FOTO -> {
                Log.d("Res", "Resultado camara: " + resultCode.toString())
                if (resultCode == Activity.RESULT_OK){
                    Log.d("Res", "Obtener Imagen")
//usando la preview
                    /*
                    val extras = data?.extras
                    val imageBitmap = extras!!.get("data") as Bitmap
                    ivFoto?.setImageBitmap(imageBitmap)
                    */
//usando la foto guardada
                    mostrarBitmap(urlFotoActual)

                    anadirImagenGaleria()
                }else{
                    Log.d("Res", "Cancelo captura")
                }
            }

            SOLICITUD_SELECCIONAR_FOTO -> {
                if (resultCode == Activity.RESULT_OK){
//usando la preview
                    /*
                    val extras = data?.extras
                    val imageBitmap = extras!!.get("data") as Bitmap
                    ivFoto?.setImageBitmap(imageBitmap)
                    */
//usando la foto guardada
                    mostrarBitmap(data?.data.toString())
                }else{
                    Log.d("Res", "Cancelo captura")
                }
            }
        }
    }

    fun mostrarBitmap(url:String){
        val uri = Uri.parse(url)
        val stream = contentResolver.openInputStream(uri)
        val imageBitmap = BitmapFactory.decodeStream(stream)
        ivFoto?.setImageBitmap(imageBitmap)
    }

    fun crearArchivoImagen(): File{
        val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        val NombreArchivoImagen = "JPEG " + timeStamp + " "
        val directorio = getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        //val directorio = Environment.getExternalStorageDirectory()
        //val directorioPictures = File(directorio.absolutePath + "/Pictures/")
        val imagen = File.createTempFile(NombreArchivoImagen, ".jpg", directorio)
        urlFotoActual = "file://" + imagen.absolutePath

        return imagen
    }

    fun anadirImagenGaleria(){
        val intent = Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE)
        val file = File(urlFotoActual)
        val uri = Uri.fromFile(file)
        intent.setData(uri)

        this.sendBroadcast(intent)
    }
}