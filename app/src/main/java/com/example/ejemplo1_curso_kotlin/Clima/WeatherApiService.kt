package com.example.ejemplo1_curso_kotlin.Clima

import io.reactivex.Observable
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApiService {
    @GET("/data/weather")
    fun ObtenerClima(@Query("id") id: String,
                     @Query("appid") appid: String,
                     @Query("units") units: String,
                     @Query("lang") lang: String
    ):Observable<Ciudad>

    companion object {
        fun create(): WeatherApiService {
            val retrofit = Retrofit.Builder()
                .addCallAdapterFactory(
                    RxJava2CallAdapterFactory.create())
                .addConverterFactory(
                    GsonConverterFactory.create())
                .baseUrl("https://api.openweathermap.org")
                .build()
            return retrofit.create(WeatherApiService::class.java)
        }
    }
}