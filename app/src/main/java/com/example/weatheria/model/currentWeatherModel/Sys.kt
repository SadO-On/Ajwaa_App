package com.example.weatheria.model.currentWeatherModel

import com.squareup.moshi.Json

data class Sys(
    val type: Int,
    val id: Int,
    val country: String,
    val sunrise: Int,
    val sunset: Int
)