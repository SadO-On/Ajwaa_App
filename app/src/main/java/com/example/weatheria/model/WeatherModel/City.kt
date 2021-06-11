package com.example.weatheria.model.WeatherModel

data class City (
    val id: Int,
    val name: String,
    val coord: Coord,
    val country: String,
    val timezone: Int,
    val sunrise: Int,
    val sunset: Int
        )