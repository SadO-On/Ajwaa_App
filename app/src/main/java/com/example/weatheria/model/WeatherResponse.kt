package com.example.weatheria.model

import kotlin.collections.List


data class WeatherResponse(
        val cod: String,
        val message: Double,
        val cnt: Int,
        val list :List<com.example.weatheria.model.List>,
        var city: City,
        )
