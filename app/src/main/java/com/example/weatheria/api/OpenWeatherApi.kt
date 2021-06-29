package com.example.weatheria.api

import com.example.weatheria.model.WeatherModel.WeatherResponse
import com.example.weatheria.model.currentWeatherModel.CurrentWeatherResponse
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

const val BASE_URL = "https://api.openweathermap.org/"

interface OpenWeatherApi {
    @GET("data/2.5/forecast")
   suspend fun getWeather(
        @Query("lat") lat: Double,
        @Query("lon") lon: Double,
        @Query("appid") apiKey: String,
        @Query("units") type : String,
        @Query("lang") lang : String
    ): Response<WeatherResponse>

   @GET("data/2.5/weather")
   suspend fun getCurrentWeatherData(
       @Query("lat") lat: Double,
       @Query("lon") lon: Double,
       @Query("appid") apiKey: String,
       @Query("units") type : String,
       @Query("lang") lang : String
   ) : Response<CurrentWeatherResponse>
}

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

object WeatherApi{
    val openWeatherApi : OpenWeatherApi by lazy {
        Retrofit.Builder()
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .baseUrl(BASE_URL)
            .build().create(OpenWeatherApi::class.java)
    }
}
