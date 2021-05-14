package com.example.weatheria

import android.widget.TextView
import androidx.databinding.BindingAdapter

@BindingAdapter("tempAdapter")
fun bindTemp(textView: TextView , list: List<com.example.weatheria.model.List>?){
    if(!list.isNullOrEmpty()){
        textView.text = list?.get(0)?.main?.temp?.toInt().toString() + "°"
    }else {
        textView.text = "--°"
    }
}
@BindingAdapter("weatherAdapter")
fun bindWeather(textView: TextView , list: List<com.example.weatheria.model.List>?){
    if(!list.isNullOrEmpty()){
        textView.text = list?.get(0)?.weather.get(0).main
    }else {
        textView.text = "--°"
    }
}