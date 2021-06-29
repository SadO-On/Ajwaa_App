package com.example.weatheria.weather

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.LocationManager
import android.net.Uri
import android.os.*
import android.provider.Settings
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.weatheria.BuildConfig
import com.example.weatheria.R
import com.example.weatheria.databinding.WeatherFragmentBinding
import com.google.android.gms.location.*
import com.google.android.material.snackbar.Snackbar


class WeatherFragment : Fragment() {

    private lateinit var fusedLocationClient: FusedLocationProviderClient

    private lateinit var weatherViewModel: WeatherViewModel

    private lateinit var binding: WeatherFragmentBinding

    private lateinit var mSwipeRefreshLayout: SwipeRefreshLayout
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
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity())
        requestPermission.launch(Manifest.permission.ACCESS_FINE_LOCATION)
        changeBackground()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mSwipeRefreshLayout = requireView().findViewById(R.id.swiperefresh)
        mSwipeRefreshLayout.setOnRefreshListener {
            getLocation()
            changeBackground()
        }
    }
    override fun onStart() {
        super.onStart()
        requestNewLocationData()
    }


    private val requestPermission =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            if (isGranted) {
                getLocation()
                Log.i(TAG, "Entered")
            } else {
                Snackbar
                    .make(
                        requireActivity().findViewById(android.R.id.content),
                        "You should allow Location permission to get Weather based on your Location",
                        Snackbar.LENGTH_INDEFINITE
                    ).setAction(R.string.setting) {
                        startActivity(Intent().apply {
                            action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
                            data = Uri.fromParts("package", BuildConfig.APPLICATION_ID, null)
                            flags = Intent.FLAG_ACTIVITY_NEW_TASK
                        })
                    }.show()
            }
        }

    @SuppressLint("MissingPermission")
    private fun getLocation() {
        if (checkPermissionGranted()) {
            if (isLocationEnabled()) {
                fusedLocationClient.lastLocation.addOnSuccessListener { location ->
                    if (location == null) {
                        Log.e(TAG, " Null value: not entered")
                        requestNewLocationData()
                    } else {
                        Log.i(TAG, location.toString())
                        weatherViewModel.getWeatherData(location)
                        weatherViewModel.getCurrentWeatherData(location)
                    }
                }
            } else {
                Toast.makeText(requireContext(), "Turn on the Location", Toast.LENGTH_LONG).show()
                val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
                requireActivity().startActivity(intent)
            }
        }else{
            requestPermission.launch(Manifest.permission.ACCESS_FINE_LOCATION)
        }
        mSwipeRefreshLayout.isRefreshing = false
    }

    private fun isLocationEnabled(): Boolean {
        var locationManager: LocationManager =
            requireActivity().getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(
            LocationManager.NETWORK_PROVIDER
        )
    }


    @SuppressLint("MissingPermission")
    private fun requestNewLocationData() {
        Log.i(TAG, "Entered requestNewLocationData")
        var locationRequest = LocationRequest.create().apply {
            interval = 4000
            fastestInterval = 2000
            numUpdates = 1
            priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        }
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity())
        fusedLocationClient.requestLocationUpdates(
            locationRequest,
            locationCallback,
            Looper.myLooper()
        )
    }

    private val locationCallback = object : LocationCallback() {

        override fun onLocationResult(p0: LocationResult) {
            Log.i(TAG, "new Location value is : " + p0.lastLocation.toString())
            var lastLocation = p0.lastLocation
            weatherViewModel.getWeatherData(lastLocation)
            weatherViewModel.getCurrentWeatherData(lastLocation)
        }

        override fun onLocationAvailability(p0: LocationAvailability) {
            super.onLocationAvailability(p0)
            Log.e(TAG, "Is Location Available" + p0.isLocationAvailable)
        }
    }

    private fun checkPermissionGranted(): Boolean {
        return ActivityCompat.checkSelfPermission(
            requireContext(),
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
            requireContext(),
            Manifest.permission.ACCESS_COARSE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
    }

    private fun changeBackground() {
        Log.i(TAG, weatherViewModel.time.value.toString())
        when {
            weatherViewModel.time.value!! in 13..17 -> {
                setDrawable(
                    R.drawable.afternoon_background,
                    R.drawable.sun2,
                    R.drawable.next_afternoon,
                    R.drawable.previous_afternoon
                )
                commonColorChanger(R.color.afternoon_color)
            }
            weatherViewModel.time.value!! >= 18 || weatherViewModel.time.value!! <= 5 -> {
                setDrawable(
                    R.drawable.background,
                    R.drawable.sun,
                    R.drawable.next,
                    R.drawable.previous
                )
                commonColorChanger(R.color.night_color)
            }
            else -> {
                setDrawable(
                    R.drawable.day,
                    R.drawable.sun,
                    R.drawable.next_morning,
                    R.drawable.previous_morning
                )
                binding.temperature.setTextColor(
                    ContextCompat.getColor(
                        requireContext(),
                        R.color.heading_morning_color
                    )
                )
                binding.weatherDescription.setTextColor(
                    ContextCompat.getColor(
                        requireContext(),
                        R.color.heading_morning_color
                    )
                )
                commonColorChanger(R.color.morning_color)
            }
        }
    }

    private fun setDrawable(background: Int, sun: Int, next: Int, previous: Int) {
        binding.background.setImageResource(background)
        binding.sun.setImageResource(sun)
        binding.next.setImageResource(next)
        binding.previous.setImageResource(previous)
    }

    private fun commonColorChanger(color: Int) {
        binding.line.setColorFilter(ContextCompat.getColor(requireContext(), color))
        binding.cityName.setTextColor(ContextCompat.getColor(requireContext(), color))
        binding.nowTempString.setTextColor(ContextCompat.getColor(requireContext(), color))
        binding.stepOne.setTextColor(ContextCompat.getColor(requireContext(), color))
        binding.stepTwo.setTextColor(ContextCompat.getColor(requireContext(), color))
        binding.stepThree.setTextColor(ContextCompat.getColor(requireContext(), color))
        binding.tempNow.setTextColor(ContextCompat.getColor(requireContext(), color))
        binding.stepOneTemp.setTextColor(ContextCompat.getColor(requireContext(), color))
        binding.stepTwoTemp.setTextColor(ContextCompat.getColor(requireContext(), color))
        binding.stepThreeTemp.setTextColor(ContextCompat.getColor(requireContext(), color))
        binding.daysChanger.setTextColor(ContextCompat.getColor(requireContext(), color))
    }

}