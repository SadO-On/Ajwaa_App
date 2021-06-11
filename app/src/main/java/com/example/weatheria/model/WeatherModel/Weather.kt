package com.example.weatheria.model.WeatherModel

data class Weather(
    val id: Int,
    val main: String,
    val description: String,
    val icon: String
)