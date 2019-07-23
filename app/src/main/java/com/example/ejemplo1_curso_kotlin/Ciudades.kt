package com.example.ejemplo1_curso_kotlin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.example.ejemplo1_curso_kotlin.Clima.Ciudad
import com.example.ejemplo1_curso_kotlin.Clima.WeatherApiService
import com.example.ejemplo1_curso_kotlin.Clima.WeatherApiServiceCall
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import java.lang.Exception
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Ciudades : AppCompatActivity() {

    val TAG = R.string.tag_ciudades_ciudad.toString()

    var disposable: Disposable? = null

    val wikiApiServe by lazy {
        WikiApiService.create()
    }

    val weatherApiServ by lazy {
        WeatherApiService.create()
    }
    val weatherApiServCall by lazy {
        WeatherApiServiceCall.create()
    }

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ciudades)


        val bArgentina = findViewById<Button>(R.id.bArgentina)
        val bMexico = findViewById<Button>(R.id.bMexico)
        val bInternet = findViewById<Button>(R.id.bInternet)
        val tiBuscador = findViewById<EditText>(R.id.tiBuscador)

        bArgentina.setOnClickListener(View.OnClickListener {
            val intent = Intent(this,MainActivity::class.java)
            intent.putExtra(TAG, "3435910")
            startActivity(intent)
        })

        bMexico.setOnClickListener(View.OnClickListener {
            val intent = Intent(this,MainActivity::class.java)
            intent.putExtra(TAG, "3991622")
            startActivity(intent)
        })

        /*bInternet.setOnClickListener(View.OnClickListener {
            if (Network.hayRed(this)) {
                Toast.makeText(this,"Hay red!", Toast.LENGTH_SHORT).show()
            }else{
                Toast.makeText(this,"No hay red!", Toast.LENGTH_SHORT).show()
            }
        })*/

        bInternet.setOnClickListener {
            if (tiBuscador.text.toString().isNotEmpty()) {
                beginSearch(tiBuscador.text.toString())
                //beginSearch("Trump")
            }
        }
//id=3435910&appid=4bec8783d763f39ddda5c6a4c2ca2c67

        val call = weatherApiServCall.ObtenerClima("3435910", "4bec8783d763f39ddda5c6a4c2ca2c67")
        call.enqueue(object : Callback<Ciudad> {
            override fun onResponse(call: Call<Ciudad>, response: Response<Ciudad>) {
                if (response.code() == 200) {
                    val weatherResponse = response.body()!!
                    val stringBuilder =
                        "Ciudad: " + weatherResponse.name + " - " + "Temperature: " + weatherResponse.main!!.temp + "k"
                    Log.d("Resultado", stringBuilder)
                }
            }

            override fun onFailure(call: Call<Ciudad>, t: Throwable) {
                Log.d("Resultado", t.message)
            }
        })
        disposable = weatherApiServ.ObtenerClima("3435910", "4bec8783d763f39ddda5c6a4c2ca2c67", "metric", "es")

            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { result ->
                    try {
                        Log.d("Resultado", "La temperatura de ${result.name} es: ${result.main!!.temp}k")
                        Toast.makeText(this, "Entro al try", Toast.LENGTH_LONG).show()
                    }catch(e: Exception){
                        Toast.makeText(this, "Hubo un error", Toast.LENGTH_LONG).show()
                    }
                },
//                { result -> Toast.makeText(this, "${result.query.searchinfo.totalhits} result found", Toast.LENGTH_LONG).show() },
                { error -> Toast.makeText(this, error.message, Toast.LENGTH_SHORT).show() }
            )
    }

    private fun beginSearch(searchString: String) {
        val tvResultado = findViewById<TextView>(R.id.tvResultado)
        disposable = wikiApiServe.hitCountCheck("query", "json", "search", searchString)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { result -> tvResultado.text = "${result.query.searchinfo.totalhits} result found" },
                //{ result -> Toast.makeText(this, "${result.query.searchinfo.totalhits} result found", Toast.LENGTH_LONG).show() },
                { error -> Toast.makeText(this, error.message, Toast.LENGTH_SHORT).show() }
            )
    }

    override fun onPause() {
        super.onPause()
        disposable?.dispose()
    }
}