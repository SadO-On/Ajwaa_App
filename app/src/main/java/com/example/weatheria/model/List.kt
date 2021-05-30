package com.example.weatheria.model

import com.squareup.moshi.Json
import kotlin.collections.List

data class List(
    val dt: Int,
    val main: Main,
    val weather: List<Weather>,
    val clouds: Clouds,
    val wind: Wind,
    val visibility: Int,
    val pop: Float,
    val sys: Sys,
    @Json(name = "dt_txt")
    val dtTxt: String
){
    fun toFormattedTime():String{
        val sdf = java.text.SimpleDateFormat("ha")
        val date = java.util.Date(dt.toLong() * 1000)
        return sdf.format(date)
    }
}