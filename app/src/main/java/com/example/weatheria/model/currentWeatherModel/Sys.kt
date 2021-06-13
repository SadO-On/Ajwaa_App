package com.example.weatheria.model.currentWeatherModel

import com.squareup.moshi.Json

data class Sys(
    val country: String,
    val sunrise: Int,
    val sunset: Int
)