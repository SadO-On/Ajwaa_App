package com.example.weatheria

import java.text.SimpleDateFormat

fun toFormattedDate(dt: String?): String{
    val parser = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
    val formatter = SimpleDateFormat("MM/dd")
   return formatter.format(parser.parse(dt))
}