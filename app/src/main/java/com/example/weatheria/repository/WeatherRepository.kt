package com.example.weatheria.repository

import com.example.weatheria.api.OpenWeatherApi
import com.example.weatheria.api.WeatherApi
import retrofit2.Retrofit

class WeatherRepository {
   private var client : OpenWeatherApi = WeatherApi.openWeatherApi

    suspend fun getWeather(lat:Double , lon:Double , apiKey : String ) = client.getWeather(lat, lon, apiKey,"metric")
}