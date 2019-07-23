package com.example.ejemplo1_curso_kotlin.Oauth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.ejemplo1_curso_kotlin.R
import kotlinx.android.synthetic.main.login.*

class Login : AppCompatActivity() {

    var fsq:Foursquare? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login)

        fsq = Foursquare(this)

        bLoguear.setOnClickListener{
            fsq?.iniciarSesion()
        }

    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        fsq?.validarActivityResult(requestCode, resultCode, data)
    }
}

