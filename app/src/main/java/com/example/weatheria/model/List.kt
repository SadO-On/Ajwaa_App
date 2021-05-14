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
)