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
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.weatheria.BuildConfig
import com.example.weatheria.R
import com.example.weatheria.databinding.WeatherFragmentBinding
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.material.snackbar.Snackbar

class WeatherFragment : Fragment() {

    private lateinit var fusedLocationClient : FusedLocationProviderClient

    private lateinit var weatherViewModel : WeatherViewModel
    private val TAG = "WeatherFragment"

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = WeatherFragmentBinding.inflate(inflater)
        binding.lifecycleOwner = this
        weatherViewModel = ViewModelProvider(this).get(WeatherViewModel::class.java)
        binding.viewModel = weatherViewModel
        requestPermission.launch(Manifest.permission.ACCESS_FINE_LOCATION)
        fusedLocationClient= LocationServices.getFusedLocationProviderClient(requireActivity())
        return  binding.root
    }

    private val requestPermission = registerForActivityResult(ActivityResultContracts.RequestPermission()){
            isGranted ->
        if(isGranted){
            //TODO permission granted
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
}