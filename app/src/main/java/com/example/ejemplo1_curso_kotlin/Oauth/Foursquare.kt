
package com.example.ejemplo1_curso_kotlin.Oauth

import android.content.Intent
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.ejemplo1_curso_kotlin.R
import com.foursquare.android.nativeoauth.FoursquareOAuth

class Foursquare(var activity: AppCompatActivity) {

    private val CODIGO_CONEXION = 200
    private val CODIGO_INTERCAMBIO_TOKEN = 201

    init {

    }

    fun iniciarSesion(){
        val intent = FoursquareOAuth.getConnectIntent(activity.applicationContext,  activity.applicationContext.getString(R.string.foursquare_client_id))
        Log.d("Res", activity.applicationContext.getString(R.string.foursquare_client_id))

        if (FoursquareOAuth.isPlayStoreIntent(intent)){
            mensaje("No tenes la app instalada")
            activity.startActivity(intent)
        }else{
            activity.startActivityForResult(intent, CODIGO_CONEXION)
        }
    }

    fun mensaje(mensaje:String){
        Toast.makeText(activity.applicationContext, mensaje,Toast.LENGTH_LONG).show()
    }

    fun validarActivityResult(requestCode: Int, resultCode: Int, data: Intent?){
        when(requestCode){
            CODIGO_CONEXION->{ conexionCompleta(resultCode, data)}
            CODIGO_INTERCAMBIO_TOKEN->{intercambioTokenCompleta(resultCode, data)}
        }
    }

    private fun conexionCompleta(resultCode: Int, data: Intent?) {
        val codigoRespuesta = FoursquareOAuth.getAuthCodeFromResult(resultCode, data)
        val exception = codigoRespuesta.exception
        if (exception == null) {
            val codigo = codigoRespuesta.code
            realizarIntercambio(codigo)
        }else
            mensaje("Fallo la auth")
        }

    private fun realizarIntercambio(codigo: String?) {
        var intent = FoursquareOAuth.getTokenExchangeIntent(activity.applicationContext, R.string.foursquare_client_id.toString(), R.string.foursquare_client_secret.toString(), codigo)
        activity.startActivityForResult(intent, CODIGO_INTERCAMBIO_TOKEN)
     }

    private fun intercambioTokenCompleta(resultCode: Int, data: Intent?) {
        val respuestaToken = FoursquareOAuth.getTokenFromResult(resultCode, data)
        val exception = respuestaToken.exception

        if (exception == null) {
            val accesToken = respuestaToken.accessToken
            mensaje("Token " + accesToken)
        }else{
            mensaje("No se puedo obtener el token")
        }
    }

}
