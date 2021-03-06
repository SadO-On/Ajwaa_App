package com.example.weatheria.weather

import android.location.Location
import android.util.Log
import androidx.lifecycle.*
import com.example.weatheria.BuildConfig
import com.example.weatheria.model.WeatherModel.WeatherResponse
import com.example.weatheria.model.currentWeatherModel.CurrentWeatherResponse
import com.example.weatheria.repository.WeatherRepository
import com.example.weatheria.toFormattedDate
import com.example.weatheria.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.Exception
import java.util.*
import kotlin.time.ExperimentalTime

class WeatherViewModel : ViewModel() {
    private val repository: WeatherRepository = WeatherRepository()
    private val _weatherDataResponse = MutableLiveData<WeatherResponse>()
    private val _currentWeatherDataResponse = MutableLiveData<CurrentWeatherResponse>()

    val language: String = Locale.getDefault().language

    private val _counter = MutableLiveData<Int>(0)
    val counter: LiveData<Int>
        get() = _counter

    private val _time = MutableLiveData<Int>()
    val time: LiveData<Int>
        get() = _time

    private val _mainTemperature = MutableLiveData<String>()
    val mainTemperature: LiveData<String>
        get() = _mainTemperature

    private val _mainWeatherStatus = MutableLiveData<String>()
    val mainWeatherStatus: LiveData<String>
        get() = _mainWeatherStatus

    private val _cityName = MutableLiveData<String>()
    val cityName: LiveData<String>
        get() = _cityName

    private val _firstTimeElement = MutableLiveData<String>()
    val firstTimeElement: LiveData<String>
        get() = _firstTimeElement

    private val _firstTemperatureElement = MutableLiveData<String>()
    val firstTemperatureElement: LiveData<String>
        get() = _firstTemperatureElement

    private val _secondTimeElement = MutableLiveData<String>()
    val secondTimeElement: LiveData<String>
        get() = _secondTimeElement

    private val _secondTemperatureElement = MutableLiveData<String>()
    val secondTemperatureElement: LiveData<String>
        get() = _secondTemperatureElement

    private val _thirdTimeElement = MutableLiveData<String>()
    val thirdTimeElement: LiveData<String>
        get() = _thirdTimeElement

    private val _thirdTemperatureElement = MutableLiveData<String>()
    val thirdTemperatureElement: LiveData<String>
        get() = _thirdTemperatureElement

    private val _fourthTimeElement = MutableLiveData<String>()
    val fourthTimeElement: LiveData<String>
        get() = _fourthTimeElement

    private val _fourthTemperatureElement = MutableLiveData<String>()
    val fourthTemperatureElement: LiveData<String>
        get() = _fourthTemperatureElement

    private val _date = MutableLiveData<String>()
    val date: LiveData<String>
        get() = _date


    fun getWeatherData(location: Location) = liveData(Dispatchers.IO) {
        try {
            val data = repository.getWeather(
                location.latitude,
                location.longitude,
                BuildConfig.openWeatherApiKey,
                language
            )
            _weatherDataResponse.postValue(data)
            emit(
                Resource.success(
                    data = data
                )
            )
        } catch (exception: Exception) {
            emit(Resource.error(data = null, message = exception.message ?: "Error Occurred!"))
        }
    }

    fun settingValuesOnWeatherDataSuccess() {
        _weatherDataResponse
        _firstTimeElement.value =
            _weatherDataResponse.value?.list?.get(0)?.toFormattedTime()
        _firstTemperatureElement.value =
            _weatherDataResponse.value?.list?.get(0)?.main?.temp?.toInt().toString()
        _secondTimeElement.value =
            _weatherDataResponse.value?.list?.get(1)?.toFormattedTime()
        _secondTemperatureElement.value =
            _weatherDataResponse.value?.list?.get(1)?.main?.temp?.toInt().toString()
        _thirdTimeElement.value =
            _weatherDataResponse.value?.list?.get(2)?.toFormattedTime()
        _thirdTemperatureElement.value =
            _weatherDataResponse.value?.list?.get(2)?.main?.temp?.toInt().toString()
        _fourthTimeElement.value =
            _weatherDataResponse.value?.list?.get(3)?.toFormattedTime()
        _fourthTemperatureElement.value =
            _weatherDataResponse.value?.list?.get(3)?.main?.temp?.toInt().toString()
        _date.value = toFormattedDate(_weatherDataResponse.value?.list?.get(0)?.dtTxt)
        _counter.value = 0
    }

    fun getCurrentWeatherData(location: Location) = liveData(Dispatchers.IO) {
        try {
            val data = repository.getCurrentWeatherData(
                location.latitude,
                location.longitude,
                BuildConfig.openWeatherApiKey,
                language
            )
            _currentWeatherDataResponse.postValue(data)

            emit(
                Resource.success(
                    data = data
                )
            )
        } catch (exception: Exception) {
            emit(Resource.error(data = null, message = exception.message ?: "Error Occurred!"))
        }
    }


    fun settingValuesOnCurrentWeatherSuccess() {
        _mainWeatherStatus.value =
            if (language == "ar") _currentWeatherDataResponse.value?.weather?.get(0)?.description else _currentWeatherDataResponse.value?.weather?.get(
                0
            )?.main
        _mainTemperature.value =
            _currentWeatherDataResponse.value?.main?.temp?.toInt().toString()
        _cityName.value = _currentWeatherDataResponse.value?.name
        _counter.value = 0
    }

    fun getTime() {
        val instance = Calendar.getInstance()
        _time.value = instance.get(Calendar.HOUR_OF_DAY)
    }

    @ExperimentalTime
    fun addCounter() {
        if (_counter.value == 5) {
            return
        }
        _counter.value = _counter.value?.plus(1)
        nextDay()
    }

    @ExperimentalTime
    private fun nextDay() {
        val calender = Calendar.getInstance()
        val today = calender.timeInMillis
        val desiredDay = Date(today + 86400000 * _counter.value!!)
        val list = _weatherDataResponse.value?.list
        if (list != null) {
            for (i in list.indices) {
                if (desiredDay.before(Date(list[i].dt * 1000L))) {
                    setVariables(i, list)
                    return
                }
            }
        }
    }

    @ExperimentalTime
    fun minusCounter() {
        if (_counter.value == 0) {
            return
        }
        _counter.value = _counter.value?.minus(1)
        previousDay()
    }

    @ExperimentalTime
    private fun previousDay() {
        val calender = Calendar.getInstance()
        val today = calender.timeInMillis
        val desiredDay = Date(today + 86400000 * _counter.value!!)
        val list = _weatherDataResponse.value?.list
        if (list != null) {
            for (i in list.indices) {
                if (Date(list[i].dt * 1000L).after(desiredDay)) {
                    setVariables(i, list)
                    return
                }
            }
        }
    }

    private fun setVariables(i: Int, list: List<com.example.weatheria.model.WeatherModel.List>) {
        _date.value = toFormattedDate(list[i].dtTxt)
        _mainWeatherStatus.value =
            if (language == "ar") _currentWeatherDataResponse.value?.weather?.get(0)?.description else _currentWeatherDataResponse.value?.weather?.get(
                0
            )?.main
        _mainTemperature.value = list[i].main.temp.toInt().toString()
        _firstTimeElement.value = list[i].toFormattedTime()
        _firstTemperatureElement.value = list[i + 1].main.temp.toInt().toString()
        _secondTimeElement.value = list[i + 2].toFormattedTime()
        _secondTemperatureElement.value = list[i + 2].main.temp.toInt().toString()
        _thirdTimeElement.value = list[i + 3].toFormattedTime()
        _thirdTemperatureElement.value = list[i + 3].main.temp.toInt().toString()
        _fourthTimeElement.value = list[i + 4].toFormattedTime()
        _fourthTemperatureElement.value = list[i + 4].main.temp.toInt().toString()
    }

}