package com.example.weatheria.model

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
    @Json(name = "sea_level")
    val seaLevel: Int,
    @Json(name = "grnd_level")
    val grandLevel: Int,
    val humidity: Int,
    @Json(name = "temp_kf")
    val tempKf: Float
){

}