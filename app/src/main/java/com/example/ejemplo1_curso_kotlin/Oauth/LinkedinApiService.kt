package com.example.ejemplo1_curso_kotlin.Oauth

import com.example.ejemplo1_curso_kotlin.Clima.Ciudad
import com.example.ejemplo1_curso_kotlin.Clima.WeatherApiService
import io.reactivex.Observable
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface LinkedinApiService {
    @GET("/authorization")
    fun ObtenerClima(@Query("response_type") response_type: String,
                     @Query("client_id") client_id: String,
                     @Query("redirect_uri") redirect_uri: String,
                     @Query("state") state: String,
                     @Query("scope") scope: String
    ): Observable<Ciudad>

    companion object {
        fun create(): WeatherApiService {
            val retrofit = Retrofit.Builder()
                .addCallAdapterFactory(
                    RxJava2CallAdapterFactory.create())
                .addConverterFactory(
                    GsonConverterFactory.create())
                .baseUrl("https://www.linkedin.com/oauth/v2")
                .build()
            return retrofit.create(WeatherApiService::class.java)
        }
    }
}