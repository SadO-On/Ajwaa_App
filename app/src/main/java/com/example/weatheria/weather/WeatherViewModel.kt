package com.example.weatheria.weather

import android.location.Location
import android.util.Log
import androidx.lifecycle.*
import com.example.weatheria.BuildConfig
import com.example.weatheria.model.WeatherModel.WeatherResponse
import com.example.weatheria.model.currentWeatherModel.CurrentWeatherResponse
import com.example.weatheria.repository.WeatherRepository
import kotlinx.coroutines.launch
import java.lang.Exception
import java.util.*

class WeatherViewModel : ViewModel(){


    private val TAG ="WeatherViewModel"

    private val repository : WeatherRepository = WeatherRepository()

    private val _weatherData = MutableLiveData<WeatherResponse>()

    val weatherData : LiveData<WeatherResponse>
     get() = _weatherData

    private val _currentWeatherData  = MutableLiveData<CurrentWeatherResponse>()

    val currentWeatherData : LiveData<CurrentWeatherResponse>
        get() = _currentWeatherData

    private val _time = MutableLiveData<Int>()

    val time : LiveData<Int>
        get() = _time

    fun getWeatherData(location : Location){
        viewModelScope.launch {
            try{
                _weatherData.value = repository.getWeather(location.latitude,location.longitude , BuildConfig.openWeatherApiKey).body()
            }catch (e:Exception){
                Log.e(TAG , e.message.toString())
            }
        }
    }

    fun getCurrentWeatherData(location: Location){
         viewModelScope.launch {
             try {
                 Log.i(TAG , "Today is:" + repository.getCurrentWeatherData(location.latitude,location.longitude , BuildConfig.openWeatherApiKey).body().toString())
                 _currentWeatherData.value = repository.getCurrentWeatherData(location.latitude,location.longitude , BuildConfig.openWeatherApiKey).body()
             }catch (e:Exception){
                 Log.e(TAG , e.message.toString())
             }

         }
    }
     fun getTime() {
        val instance = Calendar.getInstance()
        _time.value = instance.get(Calendar.HOUR_OF_DAY)
    }

}