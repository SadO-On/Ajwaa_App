package com.example.weatheria

import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.example.weatheria.model.WeatherModel.Weather

@BindingAdapter("tempAdapter")
fun bindTemp(textView: TextView , temperature : Float?){
    if (temperature != null) {
        if(!temperature.isNaN()){
            textView.text = temperature.toInt().toString() + "°"
        }else {
            textView.text = "--°"
        }
    }
}
@BindingAdapter("weatherAdapter")
fun bindWeather(textView: TextView , list: List<Weather>?){
    if(!list.isNullOrEmpty()){
        textView.text = list?.get(0)?.main
    }else {
        textView.text = "--"
    }
}
@BindingAdapter("timeAdapter")
fun bindTimeFirst(textView: TextView , time : String?){
    if(!time.isNullOrEmpty()){
        textView.text = time
    }else {
        textView.text = "---"
    }
}
