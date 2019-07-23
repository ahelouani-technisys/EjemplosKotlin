package com.example.ejemplo1_curso_kotlin.Clima

import com.example.ejemplo1_curso_kotlin.Model
import io.reactivex.Observable
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.Call

interface WeatherApiServiceCall {
    @GET("/data/2.5/weather")
    fun ObtenerClima(@Query("id") id: String,
                      @Query("appid") appid: String
    ):Call<Ciudad>

    companion object {
        fun create(): WeatherApiServiceCall {
            val retrofit = Retrofit.Builder()
                .addCallAdapterFactory(
                    RxJava2CallAdapterFactory.create())
                .addConverterFactory(
                    GsonConverterFactory.create())
                .baseUrl("https://api.openweathermap.org")
                .build()
            return retrofit.create(WeatherApiServiceCall::class.java)
        }
    }
}