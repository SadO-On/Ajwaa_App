package com.example.weatheria.weather

import android.location.Location
import android.util.Log
import androidx.lifecycle.*
import com.example.weatheria.BuildConfig
import com.example.weatheria.model.WeatherResponse
import com.example.weatheria.repository.WeatherRepository
import kotlinx.coroutines.launch
import java.lang.Exception

class WeatherViewModel : ViewModel(){

    private val TAG ="WeatherViewModel"

    private val repository : WeatherRepository = WeatherRepository()

    private val _weatherData = MutableLiveData<WeatherResponse>()

    val weatherData : LiveData<WeatherResponse>
     get() = _weatherData

    fun getWeatherData(location : Location){
        viewModelScope.launch {
            try{
                Log.i(TAG , "Here ---" + repository.getWeather(location.latitude,location.longitude , BuildConfig.openWeatherApiKey).body().toString())
                _weatherData.value = repository.getWeather(location.latitude,location.longitude , BuildConfig.openWeatherApiKey).body()

            }catch (e:Exception){
                Log.e(TAG , e.message.toString())
            }
        }
    }

}