package com.example.weatheria.api

import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

val BASE_URL ="pro.openweathermap.org/"

interface OpenWeatherApi{


}

val openWeatherApi by lazy {
    Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
        .baseUrl("")
        .build().create(OpenWeatherApi::class.java)
}