package com.example.ejemplo1_curso_kotlin.Camara

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.widget.ImageView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.app.ActivityCompat.startActivityForResult
import androidx.core.content.FileProvider
import java.io.File
import java.text.SimpleDateFormat
import java.util.*

class Fotos(private var activity: Activity, private var imageView: ImageView){

    private val SOLICITUD_TOMAR_FOTO = 1
    private val SOLICITUD_SELECCIONAR_FOTO = 2
    private val permisoCamara = android.Manifest.permission.CAMERA
    private  val permisoWriteStorage = android.Manifest.permission.WRITE_EXTERNAL_STORAGE
    private  val permisoReadStorage = android.Manifest.permission.READ_EXTERNAL_STORAGE

    var urlFotoActual = ""

    fun TomarFoto() {
        pedirPermisos()
    }

    fun seleccionarFoto() {
        pedirPermisosSeleccionarFotos()

    }

    private fun pedirPermisos() {
        val deboProveerContexto = ActivityCompat.shouldShowRequestPermissionRationale(activity, permisoCamara)

        if (deboProveerContexto) {
        }else{
        }
        solicitudPermiso()
    }

    private fun pedirPermisosSeleccionarFotos () {
        val deboProveerContexto = ActivityCompat.shouldShowRequestPermissionRationale(activity, permisoCamara)

        if (deboProveerContexto) {
        }else{
        }
        solicitudPermisoSeleccionarFoto()
    }


    private fun solicitudPermiso() {
        ActivityCompat.requestPermissions(activity,arrayOf(permisoCamara,permisoReadStorage, permisoWriteStorage), SOLICITUD_TOMAR_FOTO)
    }

    private fun solicitudPermisoSeleccionarFoto() {
        ActivityCompat.requestPermissions(activity,arrayOf(permisoReadStorage), SOLICITUD_SELECCIONAR_FOTO)
    }

    fun requestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray){
        when(requestCode){
            SOLICITUD_TOMAR_FOTO ->{
                if (grantResults.isNotEmpty() &&
                    grantResults[0] == PackageManager.PERMISSION_GRANTED &&
                    grantResults[1] == PackageManager.PERMISSION_GRANTED &&
                    grantResults[2] == PackageManager.PERMISSION_GRANTED){
                    dispararIntentTomarFoto()
                }else{
                    Toast.makeText(activity, "No diste permiso", Toast.LENGTH_LONG).show()
                }
            }

            SOLICITUD_SELECCIONAR_FOTO->{
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    dispararIntentSeleccionarFoto()
                }else{
                    Toast.makeText(activity, "No diste permiso", Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    private fun dispararIntentTomarFoto() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        if (intent.resolveActivity(activity.packageManager) != null){
            var archivoFoto: File? = null
            archivoFoto = crearArchivoImagen()

            if (archivoFoto!= null){
                val urlFoto = FileProvider.getUriForFile(activity, "com.example.ejemplo1_curso_kotlin.android.fileprovider", archivoFoto)
                intent.putExtra(MediaStore.EXTRA_OUTPUT, urlFoto)
                activity.startActivityForResult(intent, SOLICITUD_TOMAR_FOTO)

            }

        }
    }

    private fun dispararIntentSeleccionarFoto(){
        val intent = Intent(Intent.ACTION_GET_CONTENT)

        intent.setType("image/*")

        activity.startActivityForResult(Intent.createChooser(intent, "Seleccionar una foto"), SOLICITUD_SELECCIONAR_FOTO)
    }

    fun activityResult(requestCode: Int, resultCode: Int, data: Intent?){
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

    private fun mostrarBitmap(url:String){
        val uri = Uri.parse(url)
        val stream = activity.contentResolver.openInputStream(uri)
        val imageBitmap = BitmapFactory.decodeStream(stream)
        imageView?.setImageBitmap(imageBitmap)
    }

    private fun crearArchivoImagen(): File{
        val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        val NombreArchivoImagen = "JPEG " + timeStamp + " "
        val directorio = activity.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        //val directorio = Environment.getExternalStorageDirectory()
        //val directorioPictures = File(directorio.absolutePath + "/Pictures/")
        val imagen = File.createTempFile(NombreArchivoImagen, ".jpg", directorio)
        urlFotoActual = "file://" + imagen.absolutePath

        return imagen
    }

    private fun anadirImagenGaleria(){
        val intent = Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE)
        val file = File(urlFotoActual)
        val uri = Uri.fromFile(file)
        intent.setData(uri)

        activity.sendBroadcast(intent)
    }

}