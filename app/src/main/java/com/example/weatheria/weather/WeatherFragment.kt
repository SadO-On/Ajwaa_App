package com.example.weatheria.weather

import android.os.Bundle
import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.weatheria.BuildConfig
import com.example.weatheria.R
import com.example.weatheria.databinding.WeatherFragmentBinding
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.material.snackbar.Snackbar
import java.util.*

class WeatherFragment : Fragment() {

    private lateinit var fusedLocationClient : FusedLocationProviderClient

    private lateinit var weatherViewModel : WeatherViewModel

    private lateinit var binding :WeatherFragmentBinding
    private val TAG = "WeatherFragment"

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
         binding = WeatherFragmentBinding.inflate(inflater)
        binding.lifecycleOwner = this
        weatherViewModel = ViewModelProvider(this).get(WeatherViewModel::class.java)
        weatherViewModel.getTime()
        binding.viewModel = weatherViewModel
        requestPermission.launch(Manifest.permission.ACCESS_FINE_LOCATION)
        fusedLocationClient= LocationServices.getFusedLocationProviderClient(requireActivity())
        changeBackground()
        return  binding.root
    }



    private val requestPermission = registerForActivityResult(ActivityResultContracts.RequestPermission()){
            isGranted ->
        if(isGranted){
            getLocation()
            Log.i(TAG , "Entered")
        }else{
            Snackbar
                .make(requireActivity().findViewById(android.R.id.content),
                    "You should allow Location permission to get Weather based on your Location",
                    Snackbar.LENGTH_INDEFINITE).setAction(R.string.setting){
                    startActivity(Intent().apply {
                        action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
                        data = Uri.fromParts("package", BuildConfig.APPLICATION_ID, null)
                        flags = Intent.FLAG_ACTIVITY_NEW_TASK
                    })
                }.show()
        }
    }

    @SuppressLint("MissingPermission")
    private fun getLocation(){
        fusedLocationClient.lastLocation.addOnSuccessListener { location ->
            if (location != null){
                weatherViewModel.getWeatherData(location)
            }
        }
    }



    private fun changeBackground(){
        Log.i(TAG , weatherViewModel.time.value.toString())
        when {
            weatherViewModel.time.value!! in 13..17 -> {
                binding.background.setImageResource(R.drawable.afternoon_background)
                binding.sun.setImageResource(R.drawable.sun2)
                commonColorChanger(R.color.afternoon_color)
            }
            weatherViewModel.time.value!! >= 18 || weatherViewModel.time.value!! <= 5 -> {
                binding.background.setImageResource(R.drawable.background)
                binding.sun.setImageResource(R.drawable.sun)
                commonColorChanger(R.color.night_color)
            }
            else -> {
                binding.background.setImageResource(R.drawable.day)
                binding.sun.setImageResource(R.drawable.sun)
                binding.temperature.setTextColor(ContextCompat.getColor(requireContext(),R.color.heading_morning_color))
                binding.weatherDescription.setTextColor(ContextCompat.getColor(requireContext(),R.color.heading_morning_color))
                commonColorChanger(R.color.morning_color)
            }
        }
    }

    private fun commonColorChanger(color: Int) {
        binding.line.setColorFilter(ContextCompat.getColor(requireContext(),color))
        binding.cityName.setTextColor(ContextCompat.getColor(requireContext(),color))
        binding.nowTempString.setTextColor(ContextCompat.getColor(requireContext(),color))
        binding.stepOne.setTextColor(ContextCompat.getColor(requireContext(),color))
        binding.stepTwo.setTextColor(ContextCompat.getColor(requireContext(),color))
        binding.stepThree.setTextColor(ContextCompat.getColor(requireContext(),color))
        binding.tempNow.setTextColor(ContextCompat.getColor(requireContext(),color))
        binding.stepOneTemp.setTextColor(ContextCompat.getColor(requireContext(),color))
        binding.stepTwoTemp.setTextColor(ContextCompat.getColor(requireContext(),color))
        binding.stepThreeTemp.setTextColor(ContextCompat.getColor(requireContext(),color))
        binding.daysChanger.setTextColor(ContextCompat.getColor(requireContext(),color))
        binding.previous.setColorFilter(ContextCompat.getColor(requireContext(),color))
        binding.next.setColorFilter(ContextCompat.getColor(requireContext(),color))
    }
}