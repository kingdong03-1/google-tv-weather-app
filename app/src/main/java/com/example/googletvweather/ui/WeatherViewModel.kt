package com.example.googletvweather.ui

import android.app.Application
import android.content.Context
import androidx.compose.runtime.*
import androidx.lifecycle.*
import com.example.googletvweather.data.WeatherRepository
import com.example.googletvweather.model.*
import kotlinx.coroutines.launch

class WeatherViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = WeatherRepository()
    private val prefs = application.getSharedPreferences("weather_prefs", Context.MODE_PRIVATE)
    
    var zipCode by mutableStateOf(prefs.getString("last_zip", "") ?: "")
    var isLoading by mutableStateOf(false)
    var errorMessage by mutableStateOf<String?>(null)
    var weatherData by mutableStateOf<WeatherResponse?>(null)
    var radarData by mutableStateOf<RainViewerResponse?>(null)
    var currentLat by mutableStateOf(0.0)
    var currentLon by mutableStateOf(0.0)
    var currentFrameIndex by mutableStateOf(0)

    init {
        // Auto-fetch if zip exists
        if (zipCode.isNotEmpty()) {
            fetchWeather("3e7198296308bddb8204f0c43b1f4a8e")
        }
    }

    fun updateZip(newZip: String) {
        zipCode = newZip
        prefs.edit().putString("last_zip", newZip).apply()
    }

    fun fetchWeather(apiKey: String) {
        viewModelScope.launch {
            isLoading = true
            errorMessage = null
            try {
                val result = repository.fetchWeatherData(zipCode, apiKey)
                weatherData = result.weather
                radarData = result.radar
                currentLat = result.lat
                currentLon = result.lon
                startRadarLoop()
            } catch (e: Exception) {
                errorMessage = "Failed to fetch weather: ${e.localizedMessage}"
            } finally {
                isLoading = false
            }
        }
    }

    private fun startRadarLoop() {
        viewModelScope.launch {
            while (true) {
                kotlinx.coroutines.delay(500)
                val frames = radarData?.radar?.past ?: emptyList()
                if (frames.isNotEmpty()) {
                    currentFrameIndex = (currentFrameIndex + 1) % frames.size
                }
            }
        }
    }
}
