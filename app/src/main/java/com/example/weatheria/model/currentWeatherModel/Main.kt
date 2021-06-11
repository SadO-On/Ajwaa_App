package com.example.weatheria.model.currentWeatherModel

import com.squareup.moshi.Json

data class Main(
    val temp: Float,
    @Json(name = "feels_like")
    val feelsLike: Float,
    @Json(name = "temp_min")
    val tempMin: Float,
    @Json(name = "temp_max")
    val tempMax: Float,
    val pressure: Int,
    val humidity: Int,
)