package com.example.weatheria.model.currentWeatherModel

import com.example.weatheria.model.WeatherModel.Clouds
import com.example.weatheria.model.WeatherModel.Coord
import com.example.weatheria.model.WeatherModel.Weather
import kotlin.collections.List

data class CurrentWeatherResponse(
    val coord: Coord,
    val weather: List<Weather>,
    val base: String,
    val main: Main,
    val visibility: Int,
    val wind: Wind,
    val clouds: Clouds,
    val dt: Long,
    val sys: Sys,
    val timezone: Int,
    val id: Int,
    val name: String,
    val cod: Int
){

}