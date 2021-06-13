package com.example.weatheria

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.example.weatheria.model.WeatherModel.Weather

@BindingAdapter("tempAdapter")
fun bindTemp(textView: TextView ,data : String?){
    if (!data.isNullOrEmpty()) {
        textView.text = data
    } else {
            textView.text = "--°"
        }

}
@BindingAdapter("weatherAdapter")
fun bindWeather(textView: TextView , data : String ?){
    if(!data.isNullOrEmpty()){
        textView.text = data
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

@BindingAdapter("nextAdapter")
fun nextVisibility(imageView: ImageView, counter :Int?){
    if(counter == 4){
        imageView.visibility = View.INVISIBLE
    }else{
        imageView.visibility = View.VISIBLE

    }
}
@BindingAdapter("previousAdapter")
fun previousVisibility(imageView: ImageView, counter :Int?){
    if(counter == 0){
        imageView.visibility = View.INVISIBLE
    }else{
        imageView.visibility = View.VISIBLE

    }
}
