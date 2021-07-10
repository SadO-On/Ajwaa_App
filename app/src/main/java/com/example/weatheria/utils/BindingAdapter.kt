package com.example.weatheria.utils

import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.databinding.BindingAdapter

@BindingAdapter("tempAdapter")
fun bindTemp(textView: TextView ,data : String?){
    if (!data.isNullOrEmpty()) {
        textView.text = data.plus("°")
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

@BindingAdapter("dateAdapter")
fun bindDateFirst(textView: TextView , time : String?){
    if(!time.isNullOrEmpty()){
        textView.text = time
    }else {
        textView.text = "--/--"
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
@BindingAdapter("filipRtl")
fun filipImage(imageView: ImageView, lang: String?){
    if(!lang.isNullOrEmpty()){
        if(lang == "ar"){
            imageView.scaleX = -1F
        }else{
            imageView.scaleX = 1F
        }
    }
}
@BindingAdapter("progressBar")
fun bindProgressbar(progressBar: ProgressBar , data : String ?){
    if(!data.isNullOrEmpty()){
       progressBar.visibility = View.GONE
    }else {
        progressBar.visibility = View.VISIBLE
    }
}
