package com.example.ejemplo1_curso_kotlin.Ubicacion

import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.location.Location
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.core.app.ActivityCompat
import com.example.ejemplo1_curso_kotlin.R
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.tasks.OnSuccessListener

class Permiso : AppCompatActivity() {

    private val permisoFineLocation = android.Manifest.permission.ACCESS_FINE_LOCATION
    private val permisoCoarseLocation = android.Manifest.permission.ACCESS_COARSE_LOCATION

    private val  CODIGO_SOLICITUD_PERMISO = 100

    var locationRequest:LocationRequest? = null

    var fusedLocationClient: FusedLocationProviderClient? = null
    var callback: LocationCallback? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.ubicacionpermiso)

        fusedLocationClient = FusedLocationProviderClient(this)
        inicializarLocationRequest()
    }

    private fun inicializarLocationRequest(){
        locationRequest = LocationRequest()
        locationRequest?.interval = 10000
        locationRequest?.fastestInterval = 5000
        locationRequest?.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
    }

    private fun validarPermisoUbicacion(): Boolean{
        val hayUbicacionPrecisa = ActivityCompat.checkSelfPermission(this, permisoFineLocation) == PackageManager.PERMISSION_GRANTED
        val hayUbicacionOrdinaria = ActivityCompat.checkSelfPermission(this, permisoCoarseLocation) == PackageManager.PERMISSION_GRANTED

        return hayUbicacionPrecisa && hayUbicacionOrdinaria
    }

    @SuppressLint("MissingPermission")
    private fun obtenerUbicacion(){
//ULTIMA UBICACION
        /*fusedLocationClient?.lastLocation?.addOnSuccessListener(this, object: OnSuccessListener<Location> {
            override fun onSuccess(location: Location?) {
                if (location != null){
                    Toast.makeText(applicationContext,location?.latitude.toString() + " - " + location?.longitude.toString() , Toast.LENGTH_LONG).show()
                }
            }
        })*/
//UBICACION CADA VEZ Q TE MOVES
        callback = object: LocationCallback(){
            override fun onLocationResult(locationResult: LocationResult?) {
                super.onLocationResult(locationResult)

                for (ubicacion in locationResult?.locations!!){
                    Toast.makeText(applicationContext,ubicacion?.latitude.toString() + " - " + ubicacion?.longitude.toString() , Toast.LENGTH_LONG).show()
                }
            }
        }
        fusedLocationClient?.requestLocationUpdates(locationRequest, callback, null)
    }

    private fun pedirPermisos(){
        val deboProveerContexto = ActivityCompat.shouldShowRequestPermissionRationale(this, permisoFineLocation)

        if (deboProveerContexto){
            //mandar mensaje
        }
        solicitarPermiso()
    }

    private fun solicitarPermiso() {
        ActivityCompat.requestPermissions(this,arrayOf(permisoFineLocation, permisoCoarseLocation), CODIGO_SOLICITUD_PERMISO)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        Log.d("Res","requestCode:  ${requestCode.toString()}")
        Log.d("Res","permissions[0]: " + permissions[0].toString())
        Log.d("Res","permissions[1]: " + permissions[1].toString())

        when(requestCode){
            CODIGO_SOLICITUD_PERMISO ->{
                Log.d("Res","grantResults[0]: " + grantResults[0].toString())
                Log.d("Res","grantResults[1]: " + grantResults[1].toString())
                Log.d("Res",grantResults.size.toString())
                if(grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    obtenerUbicacion()
                }else{
                    Toast.makeText(this,"No diste permiso para obtener la ubicacion", Toast.LENGTH_SHORT).show()
                }
            }

        }
    }
    override fun onStart() {
        super.onStart()

        if (validarPermisoUbicacion()){
            obtenerUbicacion()
        }else{
            pedirPermisos()
        }
    }

    override fun onPause() {
        super.onPause()

        detenerActualizacionUbicacion()
    }

    private fun detenerActualizacionUbicacion() {
        fusedLocationClient?.removeLocationUpdates(callback!!)
    }
}
